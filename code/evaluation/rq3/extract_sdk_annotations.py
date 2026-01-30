#!/usr/bin/env python3
"""
从 Android SDK 源码中提取 @RequiresPermission 注解

提取所有带有 @RequiresPermission 注解的方法，作为官方 baseline
"""

import re
import os
from pathlib import Path
from tqdm import tqdm

# 路径配置
SDK_SOURCE_DIR = Path(r"D:\projects\paper\Android Dynamic Permission Mapping\code\android-sdk-sources-for-api-level-34-master\android-sdk-sources-for-api-level-34-master")
OUTPUT_FILE = Path(__file__).parent.parent / "baselines" / "sdk_annotations.txt"
OUTPUT_FILE.parent.mkdir(parents=True, exist_ok=True)

def extract_permission_methods(java_source, file_path):
    """
    提取带有 @RequiresPermission 注解的方法
    
    支持多种注解格式：
    1. @RequiresPermission(Manifest.permission.CAMERA)
    2. @RequiresPermission(android.Manifest.permission.CAMERA)
    3. @RequiresPermission(value = Manifest.permission.CAMERA)
    4. @RequiresPermission(allOf = {...})
    5. @RequiresPermission(anyOf = {...})
    """
    results = []
    
    # 匹配 @RequiresPermission 注解和紧随其后的方法
    # 支持多行注解
    pattern = r'@RequiresPermission\s*\([^)]*\)[\s\S]*?(?:public|protected|private)\s+(?:static\s+)?[\w<>\[\].,\s]+\s+(\w+)\s*\([^)]*\)'
    
    matches = re.finditer(pattern, java_source)
    
    for match in matches:
        # 提取完整的注解内容
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
                # 获取最后一个类名（最接近的）
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
                'annotation': full_text[:200]  # 保存部分注解用于调试
            })
    
    return results

def extract_permissions_from_annotation(annotation_text):
    """
    从注解文本中提取权限列表
    
    处理各种格式：
    - Manifest.permission.CAMERA
    - android.Manifest.permission.CAMERA
    - value = "android.permission.CAMERA"
    - allOf = {permission1, permission2}
    - anyOf = {permission1, permission2}
    """
    permissions = []
    
    # 匹配权限常量
    # 支持: Manifest.permission.XXX 或 android.Manifest.permission.XXX
    perm_pattern = r'(?:android\.)?Manifest\.permission\.(\w+)|"android\.permission\.(\w+)"'
    
    matches = re.finditer(perm_pattern, annotation_text)
    for match in matches:
        perm_name = match.group(1) or match.group(2)
        if perm_name:
            permissions.append(f"android.permission.{perm_name}")
    
    # 去重
    return list(set(permissions))

def batch_extraction():
    """
    遍历所有 Java 文件并提取注解
    """
    print("=" * 80)
    print("Android SDK @RequiresPermission Annotation Extractor")
    print("=" * 80)
    print(f"Source: {SDK_SOURCE_DIR}")
    print(f"Output: {OUTPUT_FILE}")
    print()
    
    # 收集所有 Java 文件
    java_files = list(SDK_SOURCE_DIR.rglob("*.java"))
    print(f"[Found] {len(java_files)} Java files")
    
    all_results = []
    error_count = 0
    
    # 处理每个文件
    for java_file in tqdm(java_files, desc="Extracting"):
        try:
            with open(java_file, 'r', encoding='utf-8', errors='ignore') as f:
                content = f.read()
                results = extract_permission_methods(content, str(java_file))
                all_results.extend(results)
        except Exception as e:
            error_count += 1
            if error_count < 5:  # 只显示前几个错误
                print(f"  [ERROR] {java_file.name}: {e}")
    
    print(f"\n[Extract] Found {len(all_results)} methods with @RequiresPermission")
    print(f"[Errors] {error_count} files failed to process")
    
    # 保存结果
    print(f"\n[Save] Writing to {OUTPUT_FILE}")
    
    with open(OUTPUT_FILE, 'w', encoding='utf-8') as f:
        # 写入头部
        f.write("# Android SDK 34 - @RequiresPermission Annotations\n")
        f.write(f"# Total methods: {len(all_results)}\n")
        f.write(f"# Format: class_name | method_name | permissions\n")
        f.write("\n")
        
        # 写入数据
        for result in sorted(all_results, key=lambda x: (x['class'], x['method'])):
            perms_str = ", ".join(result['permissions'])
            f.write(f"{result['class']}|{result['method']}|{perms_str}\n")
    
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
    
    print(f"\n[Top 10 Permissions]")
    for perm, count in sorted(perm_counts.items(), key=lambda x: x[1], reverse=True)[:10]:
        perm_short = perm.replace('android.permission.', '')
        print(f"  {perm_short}: {count} methods")
    
    return all_results

if __name__ == "__main__":
    results = batch_extraction()
    
    print("\n" + "=" * 80)
    print("Extraction Complete!")
    print("=" * 80)
    print(f"\nNext steps:")
    print(f"1. Convert to unified JSON format")
    print(f"2. Add to experiment as Baseline 2")
    print(f"3. Re-run matching and coverage calculation")

