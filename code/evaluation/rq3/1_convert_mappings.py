#!/usr/bin/env python3
"""
转换映射文件为统一格式

将不同工具的映射文件转换为统一的 JSON 格式，便于后续分析。

输入:
- Bamboo: ../../LLMPerm-master/sdk_parser/permission_api.txt
- Dynamo: ../../LLMPerm-master/sdk_parser/baselines/API_29.txt
- Arcade: (可选) ../../LLMPerm-master/sdk_parser/baselines/arcade_mapping.txt

输出:
- ../mappings/bamboo_mapping.json
- ../mappings/dynamo_mapping.json
- ../mappings/arcade_mapping.json (如果有)

运行: python 1_convert_mappings.py
"""

import json
import re
from pathlib import Path
from collections import defaultdict

# 路径配置
BASE_DIR = Path(__file__).parent.parent
MAPPINGS_DIR = BASE_DIR / "mappings"
MAPPINGS_DIR.mkdir(exist_ok=True)

# 输入文件
BAMBOO_FILE = BASE_DIR.parent / "LLMPerm-master" / "sdk_parser" / "permission_api.txt"
DYNAMO_FILE = BASE_DIR.parent / "LLMPerm-master" / "sdk_parser" / "baselines" / "API_29.txt"
ARCADE_FILE = BASE_DIR.parent / "LLMPerm-master" / "sdk_parser" / "baselines" / "arcade_mapping.txt"

# 输出文件
BAMBOO_OUTPUT = MAPPINGS_DIR / "bamboo_mapping.json"
DYNAMO_OUTPUT = MAPPINGS_DIR / "dynamo_mapping.json"
ARCADE_OUTPUT = MAPPINGS_DIR / "arcade_mapping.json"


def parse_bamboo_mapping(file_path):
    """
    解析 Bamboo 映射文件
    
    格式: filepath,method_name,Yes/No,permission_info
    示例: android-sdk-sources.../LocationManager.java,requestLocationUpdates,Yes,ACCESS_FINE_LOCATION
    """
    print(f"[Bamboo] Parsing: {file_path}")
    
    mappings = []
    api_count = defaultdict(int)
    
    with open(file_path, 'r', encoding='utf-8') as f:
        for line_num, line in enumerate(f, 1):
            line = line.strip()
            if not line or line.startswith('#'):
                continue
            
            try:
                parts = line.split(',')
                if len(parts) < 3:
                    continue
                
                filepath = parts[0]
                method_name = parts[1]
                requires_permission = parts[2].strip().lower() == 'yes'
                
                if not requires_permission:
                    continue
                
                # 提取类名（从文件路径）
                # 例如: android/location/LocationManager.java -> android.location.LocationManager
                match = re.search(r'android[/\\](.+?)\.java', filepath)
                if match:
                    class_path = match.group(1).replace('/', '.').replace('\\', '.')
                    class_name = 'android.' + class_path
                else:
                    continue
                
                # 提取权限信息
                permissions = []
                if len(parts) > 3:
                    perm_text = ','.join(parts[3:])
                    # 提取 android.permission.XXX
                    perm_matches = re.findall(r'android\.permission\.\w+', perm_text)
                    permissions = list(set(perm_matches))
                
                if not permissions:
                    permissions = ["UNKNOWN"]
                
                # 构建方法签名（简化版）
                method_signature = f"{class_name}.{method_name}"
                
                mappings.append({
                    'method_signature': method_signature,
                    'class_name': class_name,
                    'method_name': method_name,
                    'permissions': permissions,
                    'source': 'bamboo'
                })
                
                api_count[class_name] += 1
                
            except Exception as e:
                if line_num % 100 == 0:  # 每 100 行报告一次错误
                    print(f"  [WARN] Line {line_num} parse error: {e}")
                continue
    
    print(f"  [OK] Parsed: {len(mappings)} API-permission mappings")
    print(f"  [STAT] Classes: {len(api_count)}")
    
    return mappings


def parse_dynamo_mapping(file_path):
    """
    解析 Dynamo 映射文件
    
    格式: <class: returnType methodName(params)> :: [permission1] OR [permission2]
    示例: <android.location.LocationManager: void requestLocationUpdates(...)> :: [android.permission.ACCESS_FINE_LOCATION]
    """
    print(f"[Dynamo] Parsing: {file_path}")
    
    mappings = []
    api_count = defaultdict(int)
    
    with open(file_path, 'r', encoding='utf-8') as f:
        for line_num, line in enumerate(f, 1):
            line = line.strip()
            if not line or line.startswith('#'):
                continue
            
            try:
                # 分割 API 和权限
                if '::' not in line:
                    continue
                
                api_part, perm_part = line.split('::', 1)
                
                # 解析 API 签名
                # <android.location.LocationManager: void requestLocationUpdates(...)>
                match = re.match(r'<(.+?):\s*(.+?)\s+(\w+)\((.*?)\)>', api_part.strip())
                if not match:
                    continue
                
                class_name = match.group(1)
                return_type = match.group(2)
                method_name = match.group(3)
                params = match.group(4)
                
                # 解析权限
                # [android.permission.XXX] OR [android.permission.YYY]
                perm_matches = re.findall(r'android\.permission\.\w+', perm_part)
                permissions = list(set(perm_matches))
                
                if not permissions:
                    continue
                
                # 构建方法签名（包含参数类型）
                method_signature = f"{class_name}.{method_name}({params})"
                
                mappings.append({
                    'method_signature': method_signature,
                    'class_name': class_name,
                    'method_name': method_name,
                    'params': params,
                    'return_type': return_type,
                    'permissions': permissions,
                    'source': 'dynamo'
                })
                
                api_count[class_name] += 1
                
            except Exception as e:
                if line_num % 20 == 0:  # 每 20 行报告一次错误
                    print(f"  [WARN] Line {line_num} parse error: {e}")
                continue
    
    print(f"  [OK] Parsed: {len(mappings)} API-permission mappings")
    print(f"  [STAT] Classes: {len(api_count)}")
    
    return mappings


def save_mapping(mappings, output_file, source_name):
    """保存映射为 JSON 格式"""
    # 统计信息
    total_apis = len(mappings)
    unique_classes = len(set(m['class_name'] for m in mappings))
    unique_permissions = len(set(p for m in mappings for p in m['permissions']))
    
    output_data = {
        'source': source_name,
        'total_apis': total_apis,
        'unique_classes': unique_classes,
        'unique_permissions': unique_permissions,
        'mappings': mappings
    }
    
    with open(output_file, 'w', encoding='utf-8') as f:
        json.dump(output_data, f, indent=2, ensure_ascii=False)
    
    print(f"  [SAVED] {output_file.name}")
    print(f"    - APIs: {total_apis}")
    print(f"    - Classes: {unique_classes}")
    print(f"    - Permissions: {unique_permissions}")


def main():
    print("=" * 80)
    print("Mapping File Converter")
    print("=" * 80)
    print(f"Output directory: {MAPPINGS_DIR.absolute()}\n")
    
    # 1. 转换 Bamboo 映射
    if BAMBOO_FILE.exists():
        print("[1/3] Bamboo Mapping")
        bamboo_mappings = parse_bamboo_mapping(BAMBOO_FILE)
        save_mapping(bamboo_mappings, BAMBOO_OUTPUT, 'bamboo')
        print()
    else:
        print(f"[ERROR] Bamboo mapping not found: {BAMBOO_FILE}")
        print()
    
    # 2. 转换 Dynamo 映射
    if DYNAMO_FILE.exists():
        print("[2/3] Dynamo Mapping")
        dynamo_mappings = parse_dynamo_mapping(DYNAMO_FILE)
        save_mapping(dynamo_mappings, DYNAMO_OUTPUT, 'dynamo')
        print()
    else:
        print(f"[ERROR] Dynamo mapping not found: {DYNAMO_FILE}")
        print()
    
    # 3. 转换 Arcade 映射（如果存在）
    if ARCADE_FILE.exists():
        print("[3/3] Arcade Mapping")
        # Arcade 格式类似 Dynamo，可以复用解析函数
        arcade_mappings = parse_dynamo_mapping(ARCADE_FILE)
        save_mapping(arcade_mappings, ARCADE_OUTPUT, 'arcade')
        print()
    else:
        print("[3/3] Arcade Mapping")
        print(f"  [SKIP] Arcade mapping not found")
        print(f"    If needed, place Arcade mapping at: {ARCADE_FILE}")
        print()
    
    # 总结
    print("=" * 80)
    print("Conversion Complete!")
    print("=" * 80)
    
    if BAMBOO_OUTPUT.exists():
        print(f"[OK] Bamboo: {BAMBOO_OUTPUT}")
    if DYNAMO_OUTPUT.exists():
        print(f"[OK] Dynamo: {DYNAMO_OUTPUT}")
    if ARCADE_OUTPUT.exists():
        print(f"[OK] Arcade: {ARCADE_OUTPUT}")
    
    print("\nNext step:")
    print("python 2_extract_api_calls.py --parallel 8")
    print("(Requires APK dataset first)")


if __name__ == "__main__":
    main()
