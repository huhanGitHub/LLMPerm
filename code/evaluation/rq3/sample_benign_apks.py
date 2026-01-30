#!/usr/bin/env python3
"""
从良性 APK 中随机采样

从 1500 个良性 APK 中随机选择 500 个用于实验
"""

import random
from pathlib import Path
import shutil

BASE_DIR = Path(__file__).parent.parent
BENIGN_DIR = BASE_DIR / "apks" / "benign"
BENIGN_BACKUP_DIR = BASE_DIR / "apks" / "benign_backup"
TARGET_COUNT = 500

def main():
    print("=" * 80)
    print("Benign APK Sampler")
    print("=" * 80)
    
    # 获取所有良性 APK
    all_apks = list(BENIGN_DIR.glob("*.apk"))
    print(f"[Found] {len(all_apks)} benign APKs")
    
    if len(all_apks) <= TARGET_COUNT:
        print(f"[INFO] Already at or below target count. No sampling needed.")
        return
    
    # 随机采样
    random.seed(42)  # 固定种子以便重现
    selected_apks = random.sample(all_apks, TARGET_COUNT)
    excluded_apks = set(all_apks) - set(selected_apks)
    
    print(f"[Sample] Selected {len(selected_apks)} APKs for experiment")
    print(f"[Sample] Excluded {len(excluded_apks)} APKs (will be backed up)")
    
    # 创建备份目录
    BENIGN_BACKUP_DIR.mkdir(parents=True, exist_ok=True)
    
    # 移动未选中的 APK 到备份目录
    print(f"\n[Backup] Moving excluded APKs to backup...")
    for apk in excluded_apks:
        shutil.move(str(apk), str(BENIGN_BACKUP_DIR / apk.name))
    
    print(f"[OK] Backup complete: {BENIGN_BACKUP_DIR}")
    
    # 验证
    remaining = len(list(BENIGN_DIR.glob("*.apk")))
    backed_up = len(list(BENIGN_BACKUP_DIR.glob("*.apk")))
    
    print(f"\n[Verify] Benign APKs in experiment: {remaining}")
    print(f"[Verify] Benign APKs in backup: {backed_up}")
    
    assert remaining == TARGET_COUNT, f"Expected {TARGET_COUNT}, got {remaining}"
    print(f"\n✓ Sampling complete!")
    print("=" * 80)
    print(f"[Ready] Dataset for experiment:")
    print(f"  Benign:  {remaining}")
    
    # 统计恶意
    malware_dir = BASE_DIR / "apks" / "malware"
    malware_count = len(list(malware_dir.glob("*.apk")))
    print(f"  Malware: {malware_count}")
    print(f"  Total:   {remaining + malware_count}")
    print("=" * 80)

if __name__ == "__main__":
    main()

