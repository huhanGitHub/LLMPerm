#!/usr/bin/env python3
"""
从多个Android SDK版本源码中提取 @RequiresPermission 注解

支持版本：
- Android 6 (API 23) - Marshmallow
- Android 7 (API 24/25) - Nougat  
- Android 10 (API 29) - Q
- Android 15 (API 35) - V
"""

import re
import os
import json
from pathlib import Path
from tqdm import tqdm

# SDK源码路径配置
SDK_SOURCES = {
    6: {
        'name': 'Android 6 (API 23)',
        'path': r"D:\projects\paper\Android Dynamic Permission Mapping\code\android-sdk-sources-23\src",
        'download_url': 'https://dl.google.com/android/repository/sources-23_r01.zip'
    },
    7: {
        'name': 'Android 7 (API 24)',
        'path': r"D:\projects\paper\Android Dynamic Permission Mapping\code\android-sdk-sources-24\src",
        'download_url': 'https://dl.google.com/android/repository/sources-24_r01.zip'
    },
    10: {
        'name': 'Android 10 (API 29)',
        'path': r"D:\projects\paper\Android Dynamic Permission Mapping\code\android-sdk-sources-29\src",
        'download_url': 'https://dl.google.com/android/repository/sources-29_r01.zip'
    },
    15: {
        'name': 'Android 15 (API 35)',
        'path': r"D:\projects\paper\Android Dynamic Permission Mapping\code\android-sdk-sources-for-api-level-34-master\android-sdk-sources-for-api-level-34-master",
        'download_url': ''  # 已下载
    }
}

OUTPUT_DIR = Path(__file__).parent.parent / "baselines"
OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

def extract_permission_methods(java_source, file_path):
    """
    提取带有 @RequiresPermission 注解的方法
    """
    results = []
    
    # 匹配 @RequiresPermission 注解和紧随其后的方法
    pattern = r'@RequiresPermission\s*\([^)]*\)[\s\S]*?(?:public|protected|private)\s+(?:static\s+)?[\w<>\[\].,\s]+\s+(\w+)\s*\([^)]*\)'
    
    matches = re.finditer(pattern, java_source)
    
    for match in matches:
        full_text = match.group(0)
        method_name = match.group(1)
        
        # 提取权限信息
        permissions = extract_permissions_from_annotation(full_text)
        
        if permissions:
            # 提取类名
            class_pattern = r'(?:public|private|protected)?\s*(?:static\s+)?(?:final\s+)?class\s+(\w+)'
            class_match = re.search(class_pattern, java_source[:match.start()])
            class_name = "Unknown"
            if class_match:
                all_classes = re.findall(class_pattern, java_source[:match.start()])
                if all_classes:
                    class_name = all_classes[-1]
            
            # 提取包名
            package_pattern = r'package\s+([\w.]+);'
            package_match = re.search(package_pattern, java_source)
            package_name = package_match.group(1) if package_match else "unknown"
            
            full_class = f"{package_name}.{class_name}" if package_name != "unknown" else class_name
            
            results.append({
                'file': file_path,
                'class': full_class,
                'method': method_name,
                'permissions': permissions,
                'annotation': full_text[:200]
            })
    
    return results

def extract_permissions_from_annotation(annotation_text):
    """
    从注解文本中提取权限列表
    """
    permissions = []
    
    # 匹配权限常量
    perm_pattern = r'(?:android\.)?Manifest\.permission\.(\w+)|"android\.permission\.(\w+)"'
    
    matches = re.finditer(perm_pattern, annotation_text)
    for match in matches:
        perm_name = match.group(1) or match.group(2)
        if perm_name:
            permissions.append(f"android.permission.{perm_name}")
    
    return list(set(permissions))

def extract_from_sdk(android_version):
    """
    从指定Android版本SDK提取注解
    """
    config = SDK_SOURCES[android_version]
    sdk_path = Path(config['path'])
    
    print(f"\n{'='*80}")
    print(f"Extracting: {config['name']}")
    print(f"{'='*80}")
    print(f"Source: {sdk_path}")
    
    if not sdk_path.exists():
        print(f"[ERROR] SDK source not found: {sdk_path}")
        print(f"[INFO] Download URL: {config['download_url']}")
        return None
    
    # 收集所有 Java 文件
    java_files = list(sdk_path.rglob("*.java"))
    print(f"[Found] {len(java_files)} Java files")
    
    if len(java_files) == 0:
        print(f"[WARNING] No Java files found in {sdk_path}")
        return None
    
    all_results = []
    error_count = 0
    
    # 处理每个文件
    for java_file in tqdm(java_files, desc=f"Android {android_version}"):
        try:
            with open(java_file, 'r', encoding='utf-8', errors='ignore') as f:
                content = f.read()
                results = extract_permission_methods(content, str(java_file))
                all_results.extend(results)
        except Exception as e:
            error_count += 1
            if error_count < 3:
                print(f"  [ERROR] {java_file.name}: {e}")
    
    print(f"[Extract] Found {len(all_results)} methods with @RequiresPermission")
    print(f"[Errors] {error_count} files failed to process")
    
    # 保存结果
    output_file = OUTPUT_DIR / f"sdk_annotations_android{android_version}.txt"
    json_file = OUTPUT_DIR / f"sdk_annotations_android{android_version}.json"
    
    print(f"[Save] Writing to {output_file}")
    
    # 保存为文本格式
    with open(output_file, 'w', encoding='utf-8') as f:
        f.write(f"# {config['name']} - @RequiresPermission Annotations\n")
        f.write(f"# Total methods: {len(all_results)}\n")
        f.write(f"# Format: class_name | method_name | permissions\n")
        f.write("\n")
        
        for result in sorted(all_results, key=lambda x: (x['class'], x['method'])):
            perms_str = ", ".join(result['permissions'])
            f.write(f"{result['class']}|{result['method']}|{perms_str}\n")
    
    # 保存为JSON格式（用于后续处理）
    with open(json_file, 'w', encoding='utf-8') as f:
        json.dump({
            'android_version': android_version,
            'total_methods': len(all_results),
            'methods': all_results
        }, f, indent=2)
    
    print(f"[OK] Saved {len(all_results)} entries")
    
    # 统计信息
    unique_classes = len(set(r['class'] for r in all_results))
    unique_permissions = len(set(p for r in all_results for p in r['permissions']))
    
    print(f"\n[Statistics]")
    print(f"  Methods: {len(all_results)}")
    print(f"  Classes: {unique_classes}")
    print(f"  Unique permissions: {unique_permissions}")
    
    # 显示最常见的权限
    perm_counts = {}
    for result in all_results:
        for perm in result['permissions']:
            perm_counts[perm] = perm_counts.get(perm, 0) + 1
    
    print(f"\n[Top 5 Permissions]")
    for perm, count in sorted(perm_counts.items(), key=lambda x: x[1], reverse=True)[:5]:
        perm_short = perm.replace('android.permission.', '')
        print(f"  {perm_short}: {count} methods")
    
    return all_results

def main():
    """
    处理所有SDK版本
    """
    print("="*80)
    print("Android SDK @RequiresPermission Multi-Version Extractor")
    print("="*80)
    
    results_summary = {}
    
    for version in [6, 7, 10, 15]:
        results = extract_from_sdk(version)
        if results:
            results_summary[version] = len(results)
        else:
            results_summary[version] = 0
    
    # 汇总结果
    print("\n" + "="*80)
    print("Extraction Summary")
    print("="*80)
    
    for version, count in sorted(results_summary.items()):
        status = "✓" if count > 0 else "✗"
        print(f"  {status} Android {version:2d}: {count:4d} methods")
    
    print("\n[Output Location]")
    print(f"  {OUTPUT_DIR}/")
    print(f"    - sdk_annotations_android6.txt")
    print(f"    - sdk_annotations_android7.txt")
    print(f"    - sdk_annotations_android10.txt")
    print(f"    - sdk_annotations_android15.txt")
    
    print("\n[Next Steps]")
    print("  1. Download missing SDK sources (if any)")
    print("  2. Convert to unified JSON mapping format")
    print("  3. Create comprehensive comparison table")

if __name__ == "__main__":
    main()

