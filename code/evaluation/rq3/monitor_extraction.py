#!/usr/bin/env python3
"""
监控 API 提取进度
"""

import time
from pathlib import Path

BASE_DIR = Path(__file__).parent.parent
BENIGN_DIR = BASE_DIR / "apks" / "benign"
MALWARE_DIR = BASE_DIR / "apks" / "malware"
RESULTS_DIR = BASE_DIR / "results" / "api_calls"

def get_stats():
    """获取统计"""
    total_apks = len(list(BENIGN_DIR.glob("*.apk"))) + len(list(MALWARE_DIR.glob("*.apk")))
    extracted = len(list(RESULTS_DIR.glob("*.json")))
    
    if extracted > 0:
        percent = (extracted / total_apks * 100)
    else:
        percent = 0
    
    return {
        'total_apks': total_apks,
        'extracted': extracted,
        'remaining': total_apks - extracted,
        'percent': percent
    }

def show_once():
    """显示一次当前状态"""
    stats = get_stats()
    
    print("=" * 80)
    print("API Extraction Progress")
    print("=" * 80)
    print(f"Extracted: {stats['extracted']}/{stats['total_apks']} ({stats['percent']:.1f}%)")
    print(f"Remaining: {stats['remaining']}")
    print("=" * 80)
    
    if stats['extracted'] >= stats['total_apks']:
        print("Extraction complete!")

def monitor_continuous():
    """持续监控"""
    print("=" * 80)
    print("API Extraction Monitor")
    print("=" * 80)
    stats = get_stats()
    print(f"Total APKs: {stats['total_apks']}")
    print(f"Press Ctrl+C to stop monitoring\n")
    
    start_time = time.time()
    
    try:
        while True:
            stats = get_stats()
            elapsed_minutes = (time.time() - start_time) / 60
            
            if stats['extracted'] > 0 and elapsed_minutes > 0:
                rate = stats['extracted'] / elapsed_minutes
                if rate > 0:
                    eta_minutes = stats['remaining'] / rate
                    eta_hours = int(eta_minutes // 60)
                    eta_mins = int(eta_minutes % 60)
                    eta_str = f"{eta_hours}h {eta_mins}m"
                else:
                    eta_str = "calculating..."
            else:
                rate = 0
                eta_str = "calculating..."
            
            print(f"\r[{time.strftime('%H:%M:%S')}] "
                  f"{stats['extracted']}/{stats['total_apks']} ({stats['percent']:.1f}%) | "
                  f"Rate: {rate:.1f} APKs/min | "
                  f"ETA: {eta_str}",
                  end='', flush=True)
            
            if stats['extracted'] >= stats['total_apks']:
                print("\n\nExtraction complete!")
                break
            
            time.sleep(10)  # 每10秒更新一次
            
    except KeyboardInterrupt:
        print("\n\nMonitoring stopped.")
        print(f"\nFinal: {stats['extracted']}/{stats['total_apks']} ({stats['percent']:.1f}%)")

if __name__ == "__main__":
    import sys
    
    if len(sys.argv) > 1 and sys.argv[1] == "--watch":
        monitor_continuous()
    else:
        show_once()
        print("\nTip: Use 'python monitor_extraction.py --watch' for continuous monitoring")

