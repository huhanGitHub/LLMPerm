#!/usr/bin/env python3
"""
提取 APK 中的 API 调用

使用 Androguard 分析 APK 文件，提取所有 Android Framework API 调用。

输入:
- ../apks/benign/*.apk
- ../apks/malware/*.apk

输出:
- ../results/api_calls/{apk_name}.json

运行: 
  python 2_extract_api_calls.py --parallel 8
  python 2_extract_api_calls.py --resume  # 恢复中断的分析
  python 2_extract_api_calls.py --verbose  # 详细日志
"""

import os
import sys
import json
import time
import argparse
from pathlib import Path
from collections import defaultdict
from concurrent.futures import ProcessPoolExecutor, as_completed, TimeoutError
from tqdm import tqdm

# Androguard 导入
try:
    from androguard.misc import AnalyzeAPK
    from androguard.core.analysis.analysis import ExternalMethod
except ImportError:
    print("[ERROR] Androguard not installed")
    print("Install with: pip install androguard")
    sys.exit(1)

# 路径配置
BASE_DIR = Path(__file__).parent.parent
APK_BENIGN_DIR = BASE_DIR / "apks" / "benign"
APK_MALWARE_DIR = BASE_DIR / "apks" / "malware"
RESULTS_DIR = BASE_DIR / "results" / "api_calls"
RESULTS_DIR.mkdir(parents=True, exist_ok=True)

# 分析配置
TIMEOUT_SECONDS = 300  # 5 分钟超时
MAX_WORKERS = 8  # 默认并行数
VERBOSE = False


def extract_api_calls(apk_path, timeout=TIMEOUT_SECONDS):
    """
    从 APK 中提取 API 调用
    
    Returns:
        dict: {
            'apk_name': str,
            'package_name': str,
            'is_malware': bool,
            'total_methods': int,
            'api_calls': [
                {
                    'class': str,
                    'method': str,
                    'signature': str,
                    'count': int
                },
                ...
            ],
            'analysis_time': float,
            'error': None or str
        }
    """
    result = {
        'apk_name': apk_path.name,
        'package_name': None,
        'is_malware': 'malware' in str(apk_path),
        'total_methods': 0,
        'api_calls': [],
        'analysis_time': 0,
        'error': None
    }
    
    start_time = time.time()
    
    try:
        # 分析 APK
        a, d, dx = AnalyzeAPK(str(apk_path))
        
        # 获取包名
        result['package_name'] = a.get_package()
        
        # 提取所有方法调用
        api_call_count = defaultdict(int)
        
        for method in dx.get_methods():
            # 只关注应用自己的方法（非 Android Framework）
            class_name = method.get_class_name()
            if not class_name.startswith('L'):
                continue
            
            # 获取该方法调用的所有外部方法
            for call in method.get_xref_to():
                called_method = call[1]
                
                # 检查是否是 Android Framework API
                called_class = called_method.get_class_name()
                
                # 只关注 android.* 或 com.android.* API
                if (called_class.startswith('Landroid/') or 
                    called_class.startswith('Lcom/android/') or
                    called_class.startswith('Ldalvik/')):
                    
                    # 转换为标准格式
                    # Landroid/location/LocationManager; -> android.location.LocationManager
                    class_str = called_class[1:-1].replace('/', '.')
                    method_name = called_method.name  # 使用属性而非方法
                    
                    # 构建签名
                    signature = f"{class_str}.{method_name}"
                    api_call_count[signature] += 1
        
        # 转换为列表格式
        for signature, count in api_call_count.items():
            parts = signature.rsplit('.', 1)
            if len(parts) == 2:
                class_name, method_name = parts
                result['api_calls'].append({
                    'class': class_name,
                    'method': method_name,
                    'signature': signature,
                    'count': count
                })
        
        result['total_methods'] = len(api_call_count)
        result['analysis_time'] = time.time() - start_time
        
    except Exception as e:
        result['error'] = str(e)
        result['analysis_time'] = time.time() - start_time
    
    return result


def analyze_single_apk(apk_path):
    """分析单个 APK（用于并行处理）"""
    output_file = RESULTS_DIR / f"{apk_path.stem}.json"
    
    # 如果已经分析过，跳过
    if output_file.exists():
        return {
            'status': 'skipped',
            'apk': apk_path.name,
            'output': output_file
        }
    
    try:
        # 提取 API 调用
        result = extract_api_calls(apk_path, timeout=TIMEOUT_SECONDS)
        
        # 保存结果
        with open(output_file, 'w', encoding='utf-8') as f:
            json.dump(result, f, indent=2, ensure_ascii=False)
        
        if result['error']:
            return {
                'status': 'error',
                'apk': apk_path.name,
                'error': result['error'],
                'output': output_file
            }
        else:
            return {
                'status': 'success',
                'apk': apk_path.name,
                'api_count': result['total_methods'],
                'time': result['analysis_time'],
                'output': output_file
            }
            
    except Exception as e:
        return {
            'status': 'exception',
            'apk': apk_path.name,
            'error': str(e)
        }


def get_apk_list():
    """获取所有待分析的 APK"""
    apk_list = []
    
    # 良性应用
    if APK_BENIGN_DIR.exists():
        benign_apks = list(APK_BENIGN_DIR.glob("*.apk"))
        apk_list.extend(benign_apks)
        print(f"[Found] Benign APKs: {len(benign_apks)}")
    else:
        print(f"[WARN] Benign directory not found: {APK_BENIGN_DIR}")
    
    # 恶意应用
    if APK_MALWARE_DIR.exists():
        malware_apks = list(APK_MALWARE_DIR.glob("*.apk"))
        apk_list.extend(malware_apks)
        print(f"[Found] Malware APKs: {len(malware_apks)}")
    else:
        print(f"[WARN] Malware directory not found: {APK_MALWARE_DIR}")
    
    return apk_list


def analyze_batch(apk_list, max_workers=8, resume=False):
    """批量分析 APK"""
    print(f"\n[Analysis] Starting analysis of {len(apk_list)} APKs")
    print(f"  Parallel workers: {max_workers}")
    print(f"  Timeout: {TIMEOUT_SECONDS} seconds per APK")
    print(f"  Resume mode: {resume}")
    print(f"  Output: {RESULTS_DIR.absolute()}\n")
    
    # 如果 resume 模式，过滤已完成的
    if resume:
        existing = set(f.stem for f in RESULTS_DIR.glob("*.json"))
        apk_list = [apk for apk in apk_list if apk.stem not in existing]
        print(f"[Resume] Remaining APKs: {len(apk_list)}\n")
    
    results = {
        'success': 0,
        'error': 0,
        'skipped': 0,
        'exception': 0
    }
    
    total_api_calls = 0
    total_time = 0
    
    with ProcessPoolExecutor(max_workers=max_workers) as executor:
        futures = {executor.submit(analyze_single_apk, apk): apk for apk in apk_list}
        
        with tqdm(total=len(apk_list), desc="Analyzing") as pbar:
            for future in as_completed(futures):
                apk = futures[future]
                
                try:
                    result = future.result(timeout=TIMEOUT_SECONDS + 10)
                    
                    status = result['status']
                    results[status] += 1
                    
                    if status == 'success':
                        total_api_calls += result['api_count']
                        total_time += result['time']
                        if VERBOSE:
                            print(f"  [OK] {result['apk']}: {result['api_count']} APIs in {result['time']:.1f}s")
                    elif status == 'error':
                        if VERBOSE:
                            print(f"  [ERROR] {result['apk']}: {result['error']}")
                    elif status == 'skipped':
                        if VERBOSE:
                            print(f"  [SKIP] {result['apk']}: Already analyzed")
                    
                    pbar.set_postfix_str(
                        f"OK:{results['success']} ERR:{results['error']} SKIP:{results['skipped']}"
                    )
                    
                except TimeoutError:
                    results['exception'] += 1
                    if VERBOSE:
                        print(f"  [TIMEOUT] {apk.name}")
                    pbar.set_postfix_str(
                        f"OK:{results['success']} ERR:{results['error']} TIMEOUT:{results['exception']}"
                    )
                except Exception as e:
                    results['exception'] += 1
                    if VERBOSE:
                        print(f"  [EXCEPTION] {apk.name}: {e}")
                    pbar.set_postfix_str(
                        f"OK:{results['success']} ERR:{results['error']} EXC:{results['exception']}"
                    )
                
                pbar.update(1)
    
    return results, total_api_calls, total_time


def save_summary(apk_count, results, total_api_calls, total_time):
    """保存分析摘要"""
    summary_file = RESULTS_DIR / "analysis_summary.json"
    
    summary = {
        'timestamp': time.strftime('%Y-%m-%d %H:%M:%S'),
        'total_apks': apk_count,
        'successful': results['success'],
        'errors': results['error'],
        'exceptions': results['exception'],
        'skipped': results['skipped'],
        'success_rate': results['success'] / apk_count * 100 if apk_count > 0 else 0,
        'total_api_calls_extracted': total_api_calls,
        'total_analysis_time': total_time,
        'avg_analysis_time': total_time / results['success'] if results['success'] > 0 else 0,
        'avg_apis_per_apk': total_api_calls / results['success'] if results['success'] > 0 else 0
    }
    
    with open(summary_file, 'w', encoding='utf-8') as f:
        json.dump(summary, f, indent=2, ensure_ascii=False)
    
    print(f"\n[SAVED] Summary: {summary_file}")
    
    return summary


def main():
    parser = argparse.ArgumentParser(description='Extract API calls from APKs')
    parser.add_argument('--parallel', type=int, default=8, help='Number of parallel workers')
    parser.add_argument('--resume', action='store_true', help='Resume from previous run')
    parser.add_argument('--verbose', action='store_true', help='Verbose output')
    parser.add_argument('--apk-list', type=str, help='File containing list of APKs to analyze')
    
    args = parser.parse_args()
    
    global VERBOSE, MAX_WORKERS
    VERBOSE = args.verbose
    MAX_WORKERS = args.parallel
    
    print("=" * 80)
    print("API Call Extraction Tool")
    print("=" * 80)
    
    # 获取 APK 列表
    if args.apk_list:
        # 从文件读取 APK 列表
        with open(args.apk_list, 'r') as f:
            apk_paths = [Path(line.strip()) for line in f if line.strip()]
        print(f"[Input] APK list: {args.apk_list}")
        print(f"[Found] Total APKs: {len(apk_paths)}\n")
    else:
        apk_paths = get_apk_list()
        print(f"[Found] Total APKs: {len(apk_paths)}\n")
    
    if not apk_paths:
        print("[ERROR] No APKs found")
        print(f"Please ensure APKs are in:")
        print(f"  - {APK_BENIGN_DIR}")
        print(f"  - {APK_MALWARE_DIR}")
        sys.exit(1)
    
    # 分析 APK
    results, total_api_calls, total_time = analyze_batch(
        apk_paths, 
        max_workers=args.parallel,
        resume=args.resume
    )
    
    # 保存摘要
    summary = save_summary(len(apk_paths), results, total_api_calls, total_time)
    
    # 打印总结
    print("\n" + "=" * 80)
    print("Analysis Complete!")
    print("=" * 80)
    print(f"[Total] APKs: {len(apk_paths)}")
    print(f"[OK] Successful: {results['success']} ({summary['success_rate']:.1f}%)")
    print(f"[ERROR] Failed: {results['error']}")
    print(f"[TIMEOUT] Timeout: {results['exception']}")
    print(f"[SKIP] Skipped: {results['skipped']}")
    print("=" * 80)
    
    if results['success'] > 0:
        print(f"\n[Statistics]")
        print(f"  Total API calls: {total_api_calls:,}")
        print(f"  Avg per APK: {summary['avg_apis_per_apk']:.0f}")
        print(f"  Total time: {total_time/60:.1f} minutes")
        print(f"  Avg time: {summary['avg_analysis_time']:.1f} seconds/APK")
    
    print(f"\n[Output] Results directory: {RESULTS_DIR.absolute()}")
    print(f"[Output] Summary: {RESULTS_DIR / 'analysis_summary.json'}")
    
    # 评估
    if summary['success_rate'] >= 95:
        print("\n[OK] Excellent! Ready for next step")
    elif summary['success_rate'] >= 90:
        print("\n[OK] Good! Consider re-running failed APKs")
    else:
        print("\n[WARN] Low success rate. Check errors and consider:")
        print("  1. Increase timeout: --timeout 600")
        print("  2. Reduce parallelism: --parallel 4")
        print("  3. Remove problematic APKs")
    
    print("\n[Next Step]")
    print("python 3_match_mappings.py")


if __name__ == "__main__":
    main()

