#!/usr/bin/env python3
"""
检测潜在的运行时权限错误

检测 apps 中调用了需要权限的 API 但未在 AndroidManifest 中声明权限的情况
这些是潜在的 SecurityException 风险
"""

import json
import xml.etree.ElementTree as ET
from pathlib import Path
from collections import defaultdict
from tqdm import tqdm

# 路径配置
BASE_DIR = Path(__file__).parent.parent
MATCHED_DIR = BASE_DIR / "results" / "matched_mappings"
APK_BENIGN_DIR = BASE_DIR / "apks" / "benign"
APK_MALWARE_DIR = BASE_DIR / "apks" / "malware"
RESULTS_DIR = BASE_DIR / "results"
RESULTS_DIR.mkdir(parents=True, exist_ok=True)

def extract_declared_permissions_from_apk(apk_path):
    """
    从 APK 提取声明的权限（使用 Androguard）
    """
    try:
        from androguard.core.apk import APK
        apk = APK(apk_path)
        permissions = apk.get_permissions()
        return set(permissions) if permissions else set()
    except Exception as e:
        return set()

def detect_runtime_errors():
    """
    检测所有 apps 的运行时权限错误
    """
    print("=" * 80)
    print("Runtime Permission Error Detector")
    print("=" * 80)
    
    # 加载匹配结果
    print("\n[Step 1] Loading matched results...")
    matched_files = list(MATCHED_DIR.glob("*.json"))
    print(f"  [Found] {len(matched_files)} matched results")
    
    # 统计结果
    errors_by_mapping = defaultdict(lambda: {
        'apps_with_errors': set(),
        'total_errors': 0,
        'error_details': [],
        'benign_count': 0,
        'malware_count': 0
    })
    
    print("\n[Step 2] Analyzing apps for runtime errors...")
    
    for matched_file in tqdm(matched_files, desc="Analyzing"):
        try:
            # 读取匹配结果
            with open(matched_file, 'r', encoding='utf-8') as f:
                data = json.load(f)
            
            # 跳过摘要文件
            if 'apk_name' not in data or 'mappings' not in data:
                continue
            
            apk_name = data['apk_name']
            package_name = data['package_name']
            is_malware = data.get('is_malware', False)
            
            # 确定 APK 路径
            if is_malware:
                apk_path = APK_MALWARE_DIR / apk_name
            else:
                apk_path = APK_BENIGN_DIR / apk_name
            
            # 提取声明的权限
            if not apk_path.exists():
                continue
            
            declared_perms = extract_declared_permissions_from_apk(apk_path)
            
            # 对每个 mapping 检查
            for mapping_name, mapping_data in data['mappings'].items():
                matched_apis = mapping_data.get('matched_apis', [])
                
                # 检查每个匹配的 API
                errors_found = []
                for api_entry in matched_apis:
                    api_sig = api_entry.get('api_signature', '')
                    required_perms = api_entry.get('permissions', [])
                    
                    # 检查是否有未声明的权限
                    for perm in required_perms:
                        if perm not in declared_perms:
                            errors_found.append({
                                'api': api_sig,
                                'required_permission': perm,
                                'app': apk_name,
                                'package': package_name
                            })
                
                if errors_found:
                    stats = errors_by_mapping[mapping_name]
                    stats['apps_with_errors'].add(apk_name)
                    stats['total_errors'] += len(errors_found)
                    stats['error_details'].extend(errors_found)
                    
                    if is_malware:
                        stats['malware_count'] += 1
                    else:
                        stats['benign_count'] += 1
        
        except Exception as e:
            continue
    
    # 计算统计
    print("\n[Step 3] Computing statistics...")
    
    results = {}
    for mapping_name, stats in errors_by_mapping.items():
        results[mapping_name] = {
            'apps_with_errors': len(stats['apps_with_errors']),
            'total_errors': stats['total_errors'],
            'benign_apps': stats['benign_count'],
            'malware_apps': stats['malware_count'],
            'error_details': stats['error_details']
        }
    
    # 保存结果
    print("\n[Step 4] Saving results...")
    
    output_file = RESULTS_DIR / "runtime_errors_analysis.json"
    with open(output_file, 'w', encoding='utf-8') as f:
        json.dump(results, f, indent=2, ensure_ascii=False)
    
    print(f"  [SAVED] {output_file}")
    
    # 生成报告
    print("\n" + "=" * 80)
    print("Runtime Error Detection Results")
    print("=" * 80)
    
    for mapping_name in sorted(results.keys()):
        stats = results[mapping_name]
        print(f"\n{mapping_name.upper()}:")
        print(f"  Apps with errors: {stats['apps_with_errors']}")
        print(f"  Total errors: {stats['total_errors']}")
        print(f"  Benign apps: {stats['benign_apps']}")
        print(f"  Malware apps: {stats['malware_apps']}")
    
    # 计算 unique findings
    print("\n[Unique Findings Analysis]")
    
    bamboo_errors = set((e['app'], e['api'], e['required_permission']) 
                        for e in results.get('bamboo', {}).get('error_details', []))
    dynamo_errors = set((e['app'], e['api'], e['required_permission']) 
                        for e in results.get('dynamo', {}).get('error_details', []))
    sdk_errors = set((e['app'], e['api'], e['required_permission']) 
                     for e in results.get('sdk_annotations', {}).get('error_details', []))
    
    bamboo_unique = bamboo_errors - dynamo_errors - sdk_errors
    dynamo_unique = dynamo_errors - bamboo_errors - sdk_errors
    sdk_unique = sdk_errors - bamboo_errors - dynamo_errors
    
    print(f"  Bamboo unique: {len(bamboo_unique)}")
    print(f"  Dynamo unique: {len(dynamo_unique)}")
    print(f"  SDK Annotations unique: {len(sdk_unique)}")
    
    # 保存 unique findings
    unique_file = RESULTS_DIR / "runtime_errors_unique.txt"
    with open(unique_file, 'w', encoding='utf-8') as f:
        f.write("Bamboo Unique Findings:\n")
        f.write("=" * 80 + "\n")
        for app, api, perm in sorted(bamboo_unique)[:50]:  # Top 50
            f.write(f"{app}|{api}|{perm}\n")
    
    print(f"\n  [SAVED] Unique findings: {unique_file}")
    
    # 生成 LaTeX 表格
    generate_latex_table(results, len(bamboo_unique), len(dynamo_unique), len(sdk_unique))
    
    print("\n" + "=" * 80)
    print("Analysis Complete!")
    print("=" * 80)
    print("\n[Next Step]")
    print("  Check results/runtime_errors_table.tex")
    print("  Review unique findings in results/runtime_errors_unique.txt")

def generate_latex_table(results, bamboo_unique, dynamo_unique, sdk_unique):
    """
    生成 LaTeX 表格
    """
    output_file = RESULTS_DIR / "runtime_errors_table.tex"
    
    latex = []
    latex.append(r"\begin{table}[t]")
    latex.append(r"\centering")
    latex.append(r"\caption{Reliability Analysis: Potential Runtime Permission Errors Detected}")
    latex.append(r"\label{tab:runtime_errors}")
    latex.append(r"\begin{tabular}{lcccc}")
    latex.append(r"\toprule")
    latex.append(r"Mapping & Apps w/ Errors & Total Errors & Unique & Error Rate (\%) \\")
    latex.append(r"\midrule")
    
    total_apps = 1179
    
    # 排序：Dynamo, SDK, Bamboo
    for mapping_name in ['dynamo', 'sdk_annotations', 'bamboo']:
        if mapping_name not in results:
            continue
        
        stats = results[mapping_name]
        apps_with_errors = stats['apps_with_errors']
        total_errors = stats['total_errors']
        error_rate = (apps_with_errors / total_apps * 100)
        
        # 确定 unique count
        if mapping_name == 'bamboo':
            unique = bamboo_unique
        elif mapping_name == 'dynamo':
            unique = dynamo_unique
        else:
            unique = sdk_unique
        
        # 映射名称
        display_name = {
            'bamboo': r'\tool{}',
            'dynamo': 'Dynamo',
            'sdk_annotations': 'SDK Annotations'
        }.get(mapping_name, mapping_name)
        
        latex.append(f"{display_name} & {apps_with_errors} & {total_errors:,} & {unique} & {error_rate:.1f} \\\\")
    
    latex.append(r"\bottomrule")
    latex.append(r"\end{tabular}")
    latex.append(r"\end{table}")
    
    with open(output_file, 'w', encoding='utf-8') as f:
        f.write('\n'.join(latex))
    
    print(f"  [SAVED] LaTeX table: {output_file}")

if __name__ == "__main__":
    detect_runtime_errors()

