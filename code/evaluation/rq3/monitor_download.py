#!/usr/bin/env python3
"""
监控下载进度

实时显示下载状态和统计信息
"""

import time
from pathlib import Path

BASE_DIR = Path(__file__).parent.parent
BENIGN_DIR = BASE_DIR / "apks" / "benign"
MALWARE_DIR = BASE_DIR / "apks" / "malware"

TARGET_BENIGN = 500
TARGET_MALWARE = 500

def get_stats():
    """获取当前统计"""
    benign_count = len(list(BENIGN_DIR.glob("*.apk")))
    malware_count = len(list(MALWARE_DIR.glob("*.apk")))
    total = benign_count + malware_count
    
    benign_percent = (benign_count / TARGET_BENIGN * 100) if TARGET_BENIGN > 0 else 0
    malware_percent = (malware_count / TARGET_MALWARE * 100) if TARGET_MALWARE > 0 else 0
    total_percent = (total / (TARGET_BENIGN + TARGET_MALWARE) * 100)
    
    return {
        'benign': benign_count,
        'malware': malware_count,
        'total': total,
        'benign_percent': benign_percent,
        'malware_percent': malware_percent,
        'total_percent': total_percent
    }

def estimate_time(current, target, elapsed_minutes):
    """估算剩余时间"""
    if current == 0:
        return "计算中..."
    
    rate = current / elapsed_minutes  # APKs per minute
    remaining = target - current
    
    if rate > 0:
        remaining_minutes = remaining / rate
        hours = int(remaining_minutes // 60)
        minutes = int(remaining_minutes % 60)
        return f"{hours}h {minutes}m"
    else:
        return "计算中..."

def monitor_continuous():
    """持续监控"""
    print("=" * 80)
    print("AndroZoo Download Monitor")
    print("=" * 80)
    print(f"Target: {TARGET_BENIGN} benign + {TARGET_MALWARE} malware = {TARGET_BENIGN + TARGET_MALWARE} total")
    print(f"Press Ctrl+C to stop monitoring\n")
    
    start_time = time.time()
    initial_stats = get_stats()
    
    try:
        while True:
            stats = get_stats()
            elapsed_minutes = (time.time() - start_time) / 60
            
            print(f"\r[{time.strftime('%H:%M:%S')}] "
                  f"Benign: {stats['benign']}/{TARGET_BENIGN} ({stats['benign_percent']:.1f}%) | "
                  f"Malware: {stats['malware']}/{TARGET_MALWARE} ({stats['malware_percent']:.1f}%) | "
                  f"Total: {stats['total']}/{TARGET_BENIGN + TARGET_MALWARE} ({stats['total_percent']:.1f}%)", 
                  end='', flush=True)
            
            if stats['total'] >= TARGET_BENIGN + TARGET_MALWARE:
                print("\n\n✓ Download complete!")
                break
            
            time.sleep(5)  # 每5秒更新一次
            
    except KeyboardInterrupt:
        print("\n\nMonitoring stopped.")
        print(f"\nFinal stats:")
        print(f"  Benign: {stats['benign']}/{TARGET_BENIGN}")
        print(f"  Malware: {stats['malware']}/{TARGET_MALWARE}")
        print(f"  Total: {stats['total']}/{TARGET_BENIGN + TARGET_MALWARE}")

def show_once():
    """显示一次当前状态"""
    stats = get_stats()
    
    print("=" * 80)
    print("Current Download Status")
    print("=" * 80)
    print(f"Benign:  {stats['benign']}/{TARGET_BENIGN} ({stats['benign_percent']:.1f}%)")
    print(f"Malware: {stats['malware']}/{TARGET_MALWARE} ({stats['malware_percent']:.1f}%)")
    print(f"Total:   {stats['total']}/{TARGET_BENIGN + TARGET_MALWARE} ({stats['total_percent']:.1f}%)")
    print("=" * 80)
    
    if stats['total'] >= TARGET_BENIGN + TARGET_MALWARE:
        print("✓ Download complete!")
    else:
        print(f"Remaining: {TARGET_BENIGN + TARGET_MALWARE - stats['total']} APKs")

if __name__ == "__main__":
    import sys
    
    if len(sys.argv) > 1 and sys.argv[1] == "--watch":
        monitor_continuous()
    else:
        show_once()
        print("\nTip: Use 'python monitor_download.py --watch' for continuous monitoring")

