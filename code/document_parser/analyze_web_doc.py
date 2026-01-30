#!/usr/bin/env python3
"""
分析Web Documentation (permission_pairs.txt)中各Android版本的数据
"""

import re
from collections import defaultdict

# Android版本对应的API level
VERSION_API_LEVELS = {
    6: 23,   # Android 6.0 (Marshmallow)
    7: 25,   # Android 7.0/7.1 (Nougat)
    10: 29,  # Android 10 (Q)
    15: 35   # Android 15 (Vanilla Ice Cream)
}

def parse_permission_pairs(filepath):
    """
    解析permission_pairs.txt文件
    """
    entries = []
    
    with open(filepath, 'r', encoding='utf-8') as f:
        for line in f:
            line = line.strip()
            if not line:
                continue
            
            # 提取API level
            api_match = re.search(r'API level (\d+)', line)
            if api_match:
                api_level = int(api_match.group(1))
                entries.append({
                    'line': line,
                    'api_level': api_level
                })
    
    return entries

def count_by_version(entries):
    """
    统计每个Android版本可用的permission数量
    """
    results = {}
    
    for android_version, max_api_level in VERSION_API_LEVELS.items():
        # 累积到该API level为止的所有permissions
        count = sum(1 for entry in entries if entry['api_level'] <= max_api_level)
        results[android_version] = count
    
    return results

def main():
    filepath = "permission_pairs.txt"
    
    print("=" * 80)
    print("Web Documentation (permission_pairs.txt) Analysis")
    print("=" * 80)
    
    entries = parse_permission_pairs(filepath)
    print(f"\nTotal entries: {len(entries)}")
    
    # 统计每个版本
    version_counts = count_by_version(entries)
    
    print("\n" + "=" * 80)
    print("Permissions Available by Android Version")
    print("=" * 80)
    
    for version in sorted(VERSION_API_LEVELS.keys()):
        api_level = VERSION_API_LEVELS[version]
        count = version_counts[version]
        print(f"Android {version:2d} (API ≤ {api_level}): {count:3d} permissions")
    
    # 对比vsDocument.tex中的旧数据
    old_data = {
        7: 13,
        10: 57,
        15: 165
    }
    
    print("\n" + "=" * 80)
    print("Comparison with Old vsDocument.tex Data")
    print("=" * 80)
    
    for version in sorted(old_data.keys()):
        new_count = version_counts[version]
        old_count = old_data[version]
        diff = new_count - old_count
        print(f"Android {version:2d}: {new_count:3d} (new) vs {old_count:3d} (old) = {diff:+4d} diff")
    
    # 详细统计
    print("\n" + "=" * 80)
    print("API Level Distribution")
    print("=" * 80)
    
    api_counts = defaultdict(int)
    for entry in entries:
        api_counts[entry['api_level']] += 1
    
    for api_level in sorted(api_counts.keys()):
        count = api_counts[api_level]
        print(f"  API {api_level:2d}: {count:3d} permissions introduced")

if __name__ == "__main__":
    main()

