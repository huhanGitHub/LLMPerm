#!/usr/bin/env python3
"""
AndroZoo 完整数据集下载器
- 下载良性应用（Privacy + Reliability Analysis）
- 下载恶意应用（Security Analysis）

使用说明:
1. 申请 AndroZoo API key: https://androzoo.uni.lu/
2. 下载 latest.csv: https://androzoo.uni.lu/static/lists/latest.csv
3. 设置环境变量: export ANDROZOO_API_KEY="your_key_here"
4. 运行脚本: python download_androzoo_complete.py
"""

import os
import sys
import csv
import time
import hashlib
import requests
from pathlib import Path
from concurrent.futures import ThreadPoolExecutor, as_completed
from tqdm import tqdm

# 导入配置
try:
    from config import (
        ANDROZOO_API_KEY,
        BENIGN_COUNT,
        MALWARE_COUNT,
        MAX_FILE_SIZE_MB,
        MIN_DATE,
        MALWARE_THRESHOLD,
        MAX_WORKERS,
        RETRY_COUNT
    )
except ImportError:
    print("[ERROR] config.py not found!")
    print("Please create config.py with your ANDROZOO_API_KEY")
    sys.exit(1)

# API 配置
ANDROZOO_URL = "https://androzoo.uni.lu/api/download"
LATEST_CSV = "latest.csv"  # 可以是 .csv 或 .csv.gz

# 目录设置
OUTPUT_DIR = Path("../apks")
BENIGN_DIR = OUTPUT_DIR / "benign"  # 良性应用
MALWARE_DIR = OUTPUT_DIR / "malware"  # 恶意应用
BENIGN_DIR.mkdir(parents=True, exist_ok=True)
MALWARE_DIR.mkdir(parents=True, exist_ok=True)


class AndroZooCompleteDownloader:
    def __init__(self, api_key):
        if not api_key:
            raise ValueError("请设置 ANDROZOO_API_KEY 环境变量！")
        self.api_key = api_key
        self.session = requests.Session()
        self.benign_downloaded = []
        self.malware_downloaded = []
        self.failed = []
        
    def filter_apks(self, csv_file):
        """筛选良性和恶意 APK"""
        print("[Filter] Filtering APKs from CSV...")
        
        benign_candidates = []
        malware_candidates = []
        
        # 支持 .csv 和 .csv.gz 格式
        import gzip
        if csv_file.endswith('.gz'):
            f = gzip.open(csv_file, 'rt', encoding='utf-8')
        else:
            f = open(csv_file, 'r', encoding='utf-8')
        
        try:
            reader = csv.DictReader(f)
            
            for row in tqdm(reader, desc="Filtering"):
                try:
                    # 基本筛选条件
                    size_mb = int(row.get('apk_size', 0)) / (1024 * 1024)
                    if size_mb > MAX_FILE_SIZE_MB:
                        continue
                    
                    dex_date = row.get('dex_date', '')
                    if dex_date < MIN_DATE:
                        continue
                    
                    if not all(k in row for k in ['sha256', 'pkg_name', 'apk_size', 'vt_detection']):
                        continue
                    
                    vt_detection = int(row.get('vt_detection', 0))
                    
                    apk_info = {
                        'sha256': row['sha256'].lower(),  # 统一转为小写
                        'pkg_name': row['pkg_name'],
                        'size_mb': size_mb,
                        'dex_date': dex_date,
                        'vt_detection': vt_detection
                    }
                    
                    # 分类：良性 vs 恶意
                    if vt_detection == 0:
                        benign_candidates.append(apk_info)
                    elif vt_detection > MALWARE_THRESHOLD:
                        malware_candidates.append(apk_info)
                    
                    # 收集更多候选（10倍），以应对下载失败和404错误
                    if (len(benign_candidates) >= BENIGN_COUNT * 10 and 
                        len(malware_candidates) >= MALWARE_COUNT * 10):
                        break
                        
                except (ValueError, KeyError):
                    continue
        finally:
            f.close()
        
        print(f"[OK] Filtering complete:")
        print(f"   Benign candidates: {len(benign_candidates)}")
        print(f"   Malware candidates: {len(malware_candidates)}")
        
        # 返回更多候选（3倍目标数量），以应对下载失败
        return {
            'benign': benign_candidates[:BENIGN_COUNT * 3],
            'malware': malware_candidates[:MALWARE_COUNT * 3]
        }
    
    def download_apk(self, apk_info, is_malware=False):
        """下载单个 APK"""
        sha256 = apk_info['sha256']
        pkg_name = apk_info['pkg_name']
        
        output_dir = MALWARE_DIR if is_malware else BENIGN_DIR
        output_file = output_dir / f"{pkg_name}_{sha256[:8]}.apk"
        
        # 如果已存在，验证 SHA256
        if output_file.exists():
            if self.verify_sha256(output_file, sha256):
                return {
                    'status': 'exists',
                    'file': output_file,
                    'info': apk_info,
                    'is_malware': is_malware
                }
            else:
                output_file.unlink()
        
        # 下载
        url = f"{ANDROZOO_URL}?apikey={self.api_key}&sha256={sha256}"
        
        for attempt in range(RETRY_COUNT):
            try:
                response = self.session.get(url, stream=True, timeout=300)
                response.raise_for_status()
                
                with open(output_file, 'wb') as f:
                    for chunk in response.iter_content(chunk_size=8192):
                        f.write(chunk)
                
                if self.verify_sha256(output_file, sha256):
                    return {
                        'status': 'success',
                        'file': output_file,
                        'info': apk_info,
                        'is_malware': is_malware
                    }
                else:
                    output_file.unlink()
                    raise ValueError("SHA256 校验失败")
                    
            except Exception as e:
                if attempt == RETRY_COUNT - 1:
                    return {
                        'status': 'failed',
                        'error': str(e),
                        'info': apk_info,
                        'is_malware': is_malware
                    }
                time.sleep(2 ** attempt)
        
        return {
            'status': 'failed',
            'error': 'Max retries exceeded',
            'info': apk_info,
            'is_malware': is_malware
        }
    
    @staticmethod
    def verify_sha256(file_path, expected_sha256):
        """验证文件的 SHA256"""
        sha256_hash = hashlib.sha256()
        with open(file_path, 'rb') as f:
            for chunk in iter(lambda: f.read(4096), b""):
                sha256_hash.update(chunk)
        return sha256_hash.hexdigest() == expected_sha256
    
    def download_batch(self, apk_dict):
        """批量下载良性和恶意 APK"""
        benign_list = apk_dict['benign']
        malware_list = apk_dict['malware']
        
        total = len(benign_list) + len(malware_list)
        
        print(f"\n[Download] Starting download of {total} APKs...")
        print(f"  Benign apps: {len(benign_list)}")
        print(f"  Malware apps: {len(malware_list)}")
        print(f"  Output directory: {OUTPUT_DIR.absolute()}")
        print(f"  Parallel workers: {MAX_WORKERS}")
        
        # 检查已存在的文件
        existing_benign = len(list(BENIGN_DIR.glob("*.apk")))
        existing_malware = len(list(MALWARE_DIR.glob("*.apk")))
        print(f"  Existing: Benign={existing_benign}, Malware={existing_malware}")
        print(f"  Target: Benign={BENIGN_COUNT}, Malware={MALWARE_COUNT}\n")
        
        # 判断是否需要下载
        need_benign = existing_benign < BENIGN_COUNT
        need_malware = existing_malware < MALWARE_COUNT
        
        if not need_benign and not need_malware:
            print("[SKIP] Both categories already meet target counts!")
            return [], [], []
        
        if not need_benign:
            print(f"[SKIP] Benign APKs already meet target ({existing_benign}/{BENIGN_COUNT})")
        if not need_malware:
            print(f"[SKIP] Malware APKs already meet target ({existing_malware}/{MALWARE_COUNT})")
        
        # 准备下载任务（只添加需要的类别）
        tasks = []
        if need_benign:
            for apk in benign_list:
                tasks.append((apk, False))
            print(f"[QUEUE] Adding {len(benign_list)} benign candidates to download queue")
        
        if need_malware:
            for apk in malware_list:
                tasks.append((apk, True))
            print(f"[QUEUE] Adding {len(malware_list)} malware candidates to download queue")
        
        print(f"[QUEUE] Total tasks: {len(tasks)}\n")
        
        with ThreadPoolExecutor(max_workers=MAX_WORKERS) as executor:
            futures = {
                executor.submit(self.download_apk, apk, is_malware): (apk, is_malware) 
                for apk, is_malware in tasks
            }
            
            with tqdm(total=len(tasks), desc="Downloading") as pbar:
                completed_count = 0
                for future in as_completed(futures):
                    result = future.result()
                    
                    if result['status'] in ['success', 'exists']:
                        if result['is_malware']:
                            self.malware_downloaded.append(result)
                        else:
                            self.benign_downloaded.append(result)
                        
                        pbar.set_postfix_str(
                            f"OK B:{len(self.benign_downloaded)}/{BENIGN_COUNT} M:{len(self.malware_downloaded)}/{MALWARE_COUNT} | "
                            f"FAIL:{len(self.failed)}"
                        )
                    else:
                        self.failed.append(result)
                        pbar.set_postfix_str(
                            f"OK B:{len(self.benign_downloaded)}/{BENIGN_COUNT} M:{len(self.malware_downloaded)}/{MALWARE_COUNT} | "
                            f"FAIL:{len(self.failed)}"
                        )
                    
                    pbar.update(1)
                    completed_count += 1
                    
                    # 每 50 个保存一次进度
                    if completed_count % 50 == 0:
                        self.save_apk_lists()
                        sys.stdout.flush()  # 强制刷新输出
                    
                    # 达到目标后停止（允许正在运行的任务完成）
                    if (len(self.benign_downloaded) >= BENIGN_COUNT and 
                        len(self.malware_downloaded) >= MALWARE_COUNT):
                        pbar.set_description("Target reached, waiting for remaining tasks...")
                        break
        
        return self.benign_downloaded, self.malware_downloaded, self.failed
    
    def save_apk_lists(self):
        """保存 APK 清单"""
        # 良性应用清单
        benign_list_file = BENIGN_DIR / "apk_list.txt"
        with open(benign_list_file, 'w', encoding='utf-8') as f:
            f.write("# Benign Apps Dataset List\n")
            f.write(f"# Download date: {time.strftime('%Y-%m-%d %H:%M:%S')}\n")
            f.write(f"# Total: {len(self.benign_downloaded)}\n\n")
            f.write("# Format: filename | sha256 | package_name | size_mb | dex_date | vt_detection\n\n")
            
            for item in self.benign_downloaded:
                info = item['info']
                filename = item['file'].name
                f.write(f"{filename}|{info['sha256']}|{info['pkg_name']}|"
                       f"{info['size_mb']:.2f}|{info['dex_date']}|{info['vt_detection']}\n")
        
        # 恶意应用清单
        malware_list_file = MALWARE_DIR / "apk_list.txt"
        with open(malware_list_file, 'w', encoding='utf-8') as f:
            f.write("# Malware Apps Dataset List\n")
            f.write(f"# Download date: {time.strftime('%Y-%m-%d %H:%M:%S')}\n")
            f.write(f"# Total: {len(self.malware_downloaded)}\n")
            f.write(f"# Detection threshold: vt_detection > {MALWARE_THRESHOLD}\n\n")
            f.write("# Format: filename | sha256 | package_name | size_mb | dex_date | vt_detection\n\n")
            
            for item in self.malware_downloaded:
                info = item['info']
                filename = item['file'].name
                f.write(f"{filename}|{info['sha256']}|{info['pkg_name']}|"
                       f"{info['size_mb']:.2f}|{info['dex_date']}|{info['vt_detection']}\n")
        
        print(f"\n[SAVED] APK lists saved:")
        print(f"  Benign: {benign_list_file}")
        print(f"  Malware: {malware_list_file}")
        
        # 保存失败列表
        if self.failed:
            failed_file = OUTPUT_DIR / "failed_downloads.txt"
            with open(failed_file, 'w', encoding='utf-8') as f:
                f.write("# Failed APK downloads\n\n")
                for item in self.failed:
                    info = item['info']
                    error = item.get('error', 'Unknown')
                    apk_type = "Malware" if item['is_malware'] else "Benign"
                    f.write(f"{apk_type}|{info['sha256']}|{info['pkg_name']}|{error}\n")
            print(f"  Failed: {failed_file}")


def main():
    if not ANDROZOO_API_KEY:
        print("[ERROR] Please set ANDROZOO_API_KEY environment variable")
        print("\nHow to get API key:")
        print("1. Visit https://androzoo.uni.lu/")
        print("2. Register and apply for API key")
        print("3. Set environment variable: export ANDROZOO_API_KEY='your_key_here'")
        sys.exit(1)
    
    # 检查 CSV 文件（支持 .csv 和 .csv.gz）
    csv_file = None
    if Path("latest.csv.gz").exists():
        csv_file = "latest.csv.gz"
    elif Path("latest.csv").exists():
        csv_file = "latest.csv"
    else:
        print("[ERROR] CSV file not found: latest.csv or latest.csv.gz")
        print("\nDownload method:")
        print("wget https://androzoo.uni.lu/static/lists/latest.csv.gz")
        print("gunzip latest.csv.gz")
        sys.exit(1)
    
    LATEST_CSV = csv_file
    
    print("=" * 80)
    print("AndroZoo Complete Dataset Downloader")
    print("=" * 80)
    print(f"API Key: {ANDROZOO_API_KEY[:10]}...")
    print(f"CSV File: {LATEST_CSV}")
    print(f"Benign apps: {BENIGN_COUNT}")
    print(f"Malware apps: {MALWARE_COUNT}")
    print(f"Total: {BENIGN_COUNT + MALWARE_COUNT}")
    print(f"Max file size: {MAX_FILE_SIZE_MB} MB")
    print(f"Min date: {MIN_DATE}")
    print(f"Malware threshold: vt_detection > {MALWARE_THRESHOLD}")
    print("=" * 80 + "\n")
    
    print("[Dataset Usage]")
    print("  - Benign apps -> Privacy Analysis + Reliability Analysis")
    print("  - Malware apps -> Security Analysis (malware detection)")
    print()
    
    downloader = AndroZooCompleteDownloader(ANDROZOO_API_KEY)
    
    # 1. 筛选 APK
    candidates = downloader.filter_apks(LATEST_CSV)
    
    if len(candidates['benign']) < BENIGN_COUNT:
        print(f"[WARN] Benign apps found: {len(candidates['benign'])} (target: {BENIGN_COUNT})")
    if len(candidates['malware']) < MALWARE_COUNT:
        print(f"[WARN] Malware apps found: {len(candidates['malware'])} (target: {MALWARE_COUNT})")
    
    # 自动继续（不需要手动确认）
    print("\n[Auto] Proceeding with download...")
    
    # 2. 下载 APK
    benign, malware, failed = downloader.download_batch(candidates)
    
    # 3. 保存清单
    downloader.save_apk_lists()
    
    # 4. 总结
    print("\n" + "=" * 80)
    print("Download Complete!")
    print("=" * 80)
    print(f"[OK] Benign apps: {len(benign)} / {BENIGN_COUNT}")
    print(f"[OK] Malware apps: {len(malware)} / {MALWARE_COUNT}")
    print(f"[FAIL] Failed: {len(failed)}")
    print(f"[DIR] Output directories:")
    print(f"   {BENIGN_DIR.absolute()}")
    print(f"   {MALWARE_DIR.absolute()}")
    print("=" * 80)
    
    # 评估
    total_success = len(benign) + len(malware)
    total_target = BENIGN_COUNT + MALWARE_COUNT
    success_rate = total_success / total_target * 100
    
    print(f"\n[STAT] Success rate: {success_rate:.1f}%")
    if success_rate >= 95:
        print("[OK] Excellent! Ready for next step")
    elif success_rate >= 90:
        print("[OK] Good! Consider supplementing failed APKs")
    else:
        print("[WARN] Low success rate, consider retry or check network")
    
    print("\n[Next Steps]")
    print("1. Validate APKs: python validate_apks.py")
    print("2. Start analysis: python 1_convert_mappings.py")


if __name__ == "__main__":
    main()

