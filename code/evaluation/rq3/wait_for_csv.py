#!/usr/bin/env python3
"""
等待 CSV 下载完成

自动监控 latest.csv.gz 下载进度，下载完成后自动提示。

运行: python wait_for_csv.py
"""

import time
from pathlib import Path

CSV_FILE = Path("latest.csv.gz")
CHECK_INTERVAL = 30  # 每 30 秒检查一次

def main():
    print("=" * 80)
    print("CSV Download Monitor")
    print("=" * 80)
    print(f"Monitoring: {CSV_FILE.absolute()}")
    print(f"Check interval: {CHECK_INTERVAL} seconds\n")
    
    prev_size = 0
    stable_count = 0
    
    while True:
        if not CSV_FILE.exists():
            print("[ERROR] File not found")
            return
        
        current_size = CSV_FILE.stat().st_size
        size_mb = current_size / (1024 * 1024)
        
        if prev_size == 0:
            prev_size = current_size
            print(f"[Start] Current size: {size_mb:.0f} MB")
        else:
            growth = current_size - prev_size
            growth_mb = growth / (1024 * 1024)
            
            if growth == 0:
                stable_count += 1
                print(f"[Stable {stable_count}/3] Size: {size_mb:.0f} MB (no change)")
                
                if stable_count >= 3:
                    print("\n" + "=" * 80)
                    print("[COMPLETE] CSV download finished!")
                    print("=" * 80)
                    print(f"Final size: {size_mb:.0f} MB ({size_mb/1024:.2f} GB)")
                    print("\n[Next Step] Run:")
                    print("python download_androzoo_complete.py")
                    return
            else:
                stable_count = 0
                speed_mb_s = growth_mb / CHECK_INTERVAL
                print(f"[Downloading] {size_mb:.0f} MB (+{growth_mb:.1f} MB, {speed_mb_s:.2f} MB/s)")
            
            prev_size = current_size
        
        time.sleep(CHECK_INTERVAL)


if __name__ == "__main__":
    try:
        main()
    except KeyboardInterrupt:
        print("\n\n[Stopped] Monitoring stopped by user")

