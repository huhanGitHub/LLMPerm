#!/usr/bin/env python3
"""
测试单个 APK 下载，用于诊断问题
从 CSV 中读取第一个良性 APK 进行测试
"""

import requests
import time
import csv
import gzip
from config import ANDROZOO_API_KEY

ANDROZOO_URL = "https://androzoo.uni.lu/api/download"

def get_test_sha256():
    """从 CSV 中获取第一个符合条件的良性 APK"""
    print("[CSV] 从 latest.csv.gz 中查找测试 APK...")
    
    with gzip.open('latest.csv.gz', 'rt', encoding='utf-8') as f:
        reader = csv.DictReader(f)
        for row in reader:
            try:
                size_mb = int(row.get('apk_size', 0)) / (1024 * 1024)
                vt_detection = int(row.get('vt_detection', 0))
                dex_date = row.get('dex_date', '')
                
                # 查找一个小的良性 APK
                if (vt_detection == 0 and 
                    size_mb < 10 and 
                    dex_date >= '2022-01-01' and
                    'sha256' in row):
                    print(f"[OK] 找到测试 APK:")
                    print(f"   Package: {row.get('pkg_name', 'unknown')}")
                    print(f"   Size: {size_mb:.2f} MB")
                    print(f"   Date: {dex_date}")
                    return row['sha256']
            except (ValueError, KeyError):
                continue
    
    print("[ERROR] 未找到合适的测试 APK")
    return None

def test_download():
    """测试下载单个 APK"""
    print("=" * 80)
    print("AndroZoo 单个 APK 下载测试")
    print("=" * 80)
    print(f"API Key: {ANDROZOO_API_KEY[:10]}...")
    print(f"URL: {ANDROZOO_URL}")
    print("=" * 80)
    print()
    
    # 从 CSV 中获取真实的 SHA256
    test_sha256 = get_test_sha256()
    if not test_sha256:
        print("\n无法找到测试 APK，退出")
        return False
    
    print(f"\nTest SHA256: {test_sha256}")
    print()
    
    url = f"{ANDROZOO_URL}?apikey={ANDROZOO_API_KEY}&sha256={test_sha256}"
    
    print("\n[1/3] 开始连接...")
    start_time = time.time()
    
    try:
        response = requests.get(url, stream=True, timeout=60)
        connect_time = time.time() - start_time
        print(f"[OK] 连接成功! (耗时: {connect_time:.2f}s)")
        print(f"   Status code: {response.status_code}")
        print(f"   Content-Type: {response.headers.get('Content-Type')}")
        print(f"   Content-Length: {response.headers.get('Content-Length')} bytes")
        
        if response.status_code != 200:
            print(f"\n[ERROR] HTTP 错误: {response.status_code}")
            print(f"Response: {response.text[:500]}")
            return False
        
        print("\n[2/3] 开始下载...")
        output_file = "test_download.apk"
        downloaded = 0
        
        with open(output_file, 'wb') as f:
            for chunk in response.iter_content(chunk_size=8192):
                f.write(chunk)
                downloaded += len(chunk)
                if downloaded % (1024 * 1024) == 0:  # 每 1MB 打印一次
                    print(f"   已下载: {downloaded / (1024*1024):.1f} MB")
        
        download_time = time.time() - start_time
        print(f"\n[OK] 下载完成! (总耗时: {download_time:.2f}s)")
        print(f"   文件大小: {downloaded / (1024*1024):.2f} MB")
        print(f"   文件位置: {output_file}")
        
        print("\n[3/3] 测试成功!")
        print("=" * 80)
        return True
        
    except requests.exceptions.Timeout:
        print(f"\n[ERROR] 连接超时（>60秒）")
        print("可能原因：")
        print("  1. 网络连接到 androzoo.uni.lu 很慢")
        print("  2. AndroZoo 服务器响应慢")
        print("  3. 防火墙阻止连接")
        return False
        
    except requests.exceptions.ConnectionError as e:
        print(f"\n[ERROR] 连接失败: {e}")
        print("可能原因：")
        print("  1. 无法访问 androzoo.uni.lu")
        print("  2. 网络配置问题")
        print("  3. 需要代理")
        return False
        
    except Exception as e:
        print(f"\n[ERROR] 未知错误: {e}")
        return False

if __name__ == "__main__":
    success = test_download()
    if success:
        print("\n下一步：运行完整下载脚本")
        print("python download_androzoo_complete.py")
    else:
        print("\n请解决上述问题后再继续")

