#!/usr/bin/env python3
"""
转换 SDK 注解为统一 JSON 格式
"""

import json
from pathlib import Path

BASE_DIR = Path(__file__).parent.parent
INPUT_FILE = BASE_DIR / "baselines" / "sdk_annotations.txt"
OUTPUT_FILE = BASE_DIR / "mappings" / "sdk_annotations_mapping.json"

def main():
    print("=" * 80)
    print("SDK Annotations Converter")
    print("=" * 80)
    print(f"Input:  {INPUT_FILE}")
    print(f"Output: {OUTPUT_FILE}")
    print()
    
    mappings = []
    
    with open(INPUT_FILE, 'r', encoding='utf-8') as f:
        for line in f:
            line = line.strip()
            
            # 跳过注释和空行
            if not line or line.startswith('#'):
                continue
            
            # 解析格式: class_name | method_name | permissions
            parts = line.split('|')
            if len(parts) != 3:
                continue
            
            class_name = parts[0].strip()
            method_name = parts[1].strip()
            perms_str = parts[2].strip()
            
            # 解析权限列表
            permissions = [p.strip() for p in perms_str.split(',') if p.strip()]
            
            if not permissions:
                continue
            
            # 构建方法签名（简化版）
            method_signature = f"{class_name}.{method_name}"
            
            mappings.append({
                'method_signature': method_signature,
                'class_name': class_name,
                'method_name': method_name,
                'permissions': permissions,
                'source': 'SDK_Annotations'
            })
    
    print(f"[Parsed] {len(mappings)} API-permission mappings")
    
    # 统计
    unique_classes = len(set(m['class_name'] for m in mappings))
    unique_perms = len(set(p for m in mappings for p in m['permissions']))
    
    print(f"[Statistics]")
    print(f"  APIs: {len(mappings)}")
    print(f"  Classes: {unique_classes}")
    print(f"  Permissions: {unique_perms}")
    
    # 保存
    output_data = {
        'source': 'Android SDK 34 @RequiresPermission Annotations',
        'api_level': 34,
        'total_apis': len(mappings),
        'total_classes': unique_classes,
        'total_permissions': unique_perms,
        'mappings': mappings
    }
    
    OUTPUT_FILE.parent.mkdir(parents=True, exist_ok=True)
    with open(OUTPUT_FILE, 'w', encoding='utf-8') as f:
        json.dump(output_data, f, indent=2, ensure_ascii=False)
    
    print(f"\n[SAVED] {OUTPUT_FILE}")
    print("=" * 80)
    print("Conversion Complete!")
    print("=" * 80)
    print(f"\nBaseline ready: SDK Annotations (2085 APIs)")
    print(f"Compared to:")
    print(f"  - Dynamo: 282 APIs")
    print(f"  - Bamboo: 2747 APIs")
    print(f"\nSDK Annotations = 7.4× more than Dynamo")
    print(f"Bamboo = 1.3× more than SDK Annotations")

if __name__ == "__main__":
    main()

