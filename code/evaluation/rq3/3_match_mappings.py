#!/usr/bin/env python3
"""
匹配 API 调用和映射文件

将提取的 API 调用与不同的映射文件匹配，统计覆盖率。

输入:
- ../results/api_calls/*.json (API 调用)
- ../mappings/*.json (映射文件)

输出:
- ../results/matched_mappings/*.json

运行: python 3_match_mappings.py
"""

import json
from pathlib import Path
from collections import defaultdict
from tqdm import tqdm

# 路径配置
BASE_DIR = Path(__file__).parent.parent
API_CALLS_DIR = BASE_DIR / "results" / "api_calls"
MAPPINGS_DIR = BASE_DIR / "mappings"
MATCHED_DIR = BASE_DIR / "results" / "matched_mappings"
MATCHED_DIR.mkdir(parents=True, exist_ok=True)


def load_mapping(mapping_file):
    """加载映射文件"""
    with open(mapping_file, 'r', encoding='utf-8') as f:
        data = json.load(f)
    
    # 创建快速查找字典
    # 支持两种匹配：完整签名 和 类名+方法名
    mapping_dict = {}
    
    for entry in data['mappings']:
        signature = entry['method_signature']
        class_name = entry['class_name']
        method_name = entry['method_name']
        permissions = entry['permissions']
        
        # 完整签名匹配
        mapping_dict[signature] = permissions
        
        # 简化匹配（类名.方法名）
        simple_sig = f"{class_name}.{method_name}"
        if simple_sig not in mapping_dict:
            mapping_dict[simple_sig] = permissions
    
    return mapping_dict, data['source']


def match_api_calls(api_calls_file, mappings):
    """匹配单个 APK 的 API 调用"""
    # 读取 API 调用
    with open(api_calls_file, 'r', encoding='utf-8') as f:
        api_data = json.load(f)
    
    result = {
        'apk_name': api_data['apk_name'],
        'package_name': api_data['package_name'],
        'is_malware': api_data['is_malware'],
        'total_api_calls': api_data['total_methods'],
        'mappings': {}
    }
    
    # 对每个映射进行匹配
    for mapping_name, (mapping_dict, source) in mappings.items():
        matched_apis = []
        permissions_required = set()
        sensitive_api_call_count = 0
        
        for api_call in api_data['api_calls']:
            signature = api_call['signature']
            count = api_call['count']
            
            # 尝试匹配
            permissions = None
            if signature in mapping_dict:
                permissions = mapping_dict[signature]
            
            if permissions:
                matched_apis.append({
                    'signature': signature,
                    'count': count,
                    'permissions': permissions
                })
                permissions_required.update(permissions)
                sensitive_api_call_count += count
        
        result['mappings'][mapping_name] = {
            'source': source,
            'total_api_calls': api_data['total_methods'],
            'sensitive_api_calls': sensitive_api_call_count,
            'unique_sensitive_apis': len(matched_apis),
            'permissions_required': sorted(list(permissions_required)),
            'matched_apis': matched_apis
        }
    
    return result


def main():
    print("=" * 80)
    print("API Call Mapping Matcher")
    print("=" * 80)
    
    # 1. 加载映射文件
    print("\n[Step 1] Loading mappings...")
    mappings = {}
    
    bamboo_file = MAPPINGS_DIR / "bamboo_mapping.json"
    dynamo_file = MAPPINGS_DIR / "dynamo_mapping.json"
    arcade_file = MAPPINGS_DIR / "arcade_mapping.json"
    sdk_annotations_file = MAPPINGS_DIR / "sdk_annotations_mapping.json"
    
    if bamboo_file.exists():
        mappings['bamboo'] = load_mapping(bamboo_file)
        print(f"  [OK] Bamboo: {len(mappings['bamboo'][0])} entries")
    else:
        print(f"  [WARN] Bamboo mapping not found")
    
    if dynamo_file.exists():
        mappings['dynamo'] = load_mapping(dynamo_file)
        print(f"  [OK] Dynamo: {len(mappings['dynamo'][0])} entries")
    else:
        print(f"  [WARN] Dynamo mapping not found")
    
    if sdk_annotations_file.exists():
        mappings['sdk_annotations'] = load_mapping(sdk_annotations_file)
        print(f"  [OK] SDK Annotations: {len(mappings['sdk_annotations'][0])} entries")
    else:
        print(f"  [SKIP] SDK Annotations mapping not found")
    
    if arcade_file.exists():
        mappings['arcade'] = load_mapping(arcade_file)
        print(f"  [OK] Arcade: {len(mappings['arcade'][0])} entries")
    else:
        print(f"  [SKIP] Arcade mapping not found")
    
    if not mappings:
        print("\n[ERROR] No mappings loaded!")
        print("Please run: python 1_convert_mappings.py")
        return
    
    # 2. 获取 API 调用文件列表
    print("\n[Step 2] Loading API calls...")
    api_call_files = list(API_CALLS_DIR.glob("*.json"))
    
    if not api_call_files:
        print(f"[ERROR] No API call files found in {API_CALLS_DIR}")
        print("Please run: python 2_extract_api_calls.py")
        return
    
    print(f"  [Found] {len(api_call_files)} API call files")
    
    # 3. 匹配所有 APK
    print("\n[Step 3] Matching API calls with mappings...")
    
    success_count = 0
    error_count = 0
    
    for api_file in tqdm(api_call_files, desc="Matching"):
        try:
            # 匹配
            result = match_api_calls(api_file, mappings)
            
            # 保存结果
            output_file = MATCHED_DIR / api_file.name
            with open(output_file, 'w', encoding='utf-8') as f:
                json.dump(result, f, indent=2, ensure_ascii=False)
            
            success_count += 1
            
        except Exception as e:
            error_count += 1
            print(f"  [ERROR] {api_file.name}: {e}")
    
    # 4. 生成匹配摘要
    print("\n[Step 4] Generating summary...")
    
    summary = {
        'total_apks': len(api_call_files),
        'successful': success_count,
        'errors': error_count,
        'mappings_used': list(mappings.keys()),
        'per_mapping_stats': defaultdict(lambda: {
            'total_sensitive_calls': 0,
            'total_unique_apis': 0,
            'benign_count': 0,
            'malware_count': 0
        })
    }
    
    # 统计每个映射的总体情况
    for matched_file in MATCHED_DIR.glob("*.json"):
        with open(matched_file, 'r', encoding='utf-8') as f:
            data = json.load(f)
        
        # 跳过摘要文件
        if 'is_malware' not in data or 'mappings' not in data:
            continue
        
        is_malware = data['is_malware']
        
        for mapping_name, mapping_data in data['mappings'].items():
            stats = summary['per_mapping_stats'][mapping_name]
            stats['total_sensitive_calls'] += mapping_data['sensitive_api_calls']
            stats['total_unique_apis'] += mapping_data['unique_sensitive_apis']
            
            if is_malware:
                stats['malware_count'] += 1
            else:
                stats['benign_count'] += 1
    
    # 保存摘要
    summary_file = MATCHED_DIR / "matching_summary.json"
    with open(summary_file, 'w', encoding='utf-8') as f:
        json.dump(summary, f, indent=2, ensure_ascii=False)
    
    # 打印总结
    print("\n" + "=" * 80)
    print("Matching Complete!")
    print("=" * 80)
    print(f"[Total] APKs: {len(api_call_files)}")
    print(f"[OK] Successful: {success_count}")
    print(f"[ERROR] Failed: {error_count}")
    print("=" * 80)
    
    print("\n[Per-Mapping Statistics]")
    for mapping_name, stats in summary['per_mapping_stats'].items():
        print(f"\n{mapping_name.upper()}:")
        print(f"  Total sensitive API calls: {stats['total_sensitive_calls']:,}")
        print(f"  Avg unique APIs per APK: {stats['total_unique_apis']/success_count:.1f}")
        print(f"  Benign apps: {stats['benign_count']}")
        print(f"  Malware apps: {stats['malware_count']}")
    
    print(f"\n[Output] Matched results: {MATCHED_DIR.absolute()}")
    print(f"[Output] Summary: {summary_file}")
    
    print("\n[Next Step]")
    print("python 4_calculate_coverage.py")


if __name__ == "__main__":
    main()

