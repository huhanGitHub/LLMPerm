#!/usr/bin/env python3
"""
计算覆盖率统计

基于匹配结果，计算不同映射的覆盖率统计。

输入:
- ../results/matched_mappings/*.json

输出:
- ../results/coverage_stats.csv
- ../results/coverage_details.json

运行: python 4_calculate_coverage.py
"""

import json
import csv
from pathlib import Path
from collections import defaultdict

# 路径配置
BASE_DIR = Path(__file__).parent.parent
MATCHED_DIR = BASE_DIR / "results" / "matched_mappings"
OUTPUT_CSV = BASE_DIR / "results" / "coverage_stats.csv"
OUTPUT_JSON = BASE_DIR / "results" / "coverage_details.json"


def load_matched_results():
    """加载所有匹配结果"""
    print("[Load] Loading matched results...")
    
    results = []
    matched_files = list(MATCHED_DIR.glob("*.json"))
    
    for file in matched_files:
        with open(file, 'r', encoding='utf-8') as f:
            data = json.load(f)
            results.append(data)
    
    print(f"  [OK] Loaded {len(results)} results")
    return results


def calculate_coverage(results):
    """计算覆盖率统计"""
    print("\n[Calculate] Computing coverage statistics...")
    
    # 统计每个映射的数据
    stats = defaultdict(lambda: {
        'total_sensitive_calls': 0,
        'total_unique_apis': 0,
        'total_apps': 0,
        'benign_apps': 0,
        'malware_apps': 0,
        'apps_with_sensitive_apis': 0,
        'per_app_coverage': []
    })
    
    # 收集数据
    for result in results:
        # 跳过无效结果（如 analysis_summary.json）
        if 'mappings' not in result:
            continue
            
        for mapping_name, mapping_data in result['mappings'].items():
            stats[mapping_name]['total_sensitive_calls'] += mapping_data['sensitive_api_calls']
            stats[mapping_name]['total_unique_apis'] += mapping_data['unique_sensitive_apis']
            stats[mapping_name]['total_apps'] += 1
            
            if mapping_data['unique_sensitive_apis'] > 0:
                stats[mapping_name]['apps_with_sensitive_apis'] += 1
            
            if result['is_malware']:
                stats[mapping_name]['malware_apps'] += 1
            else:
                stats[mapping_name]['benign_apps'] += 1
    
    # 计算相对覆盖率（以 Bamboo 为基准 = 100%）
    bamboo_total = stats['bamboo']['total_sensitive_calls'] if 'bamboo' in stats else 1
    
    coverage_table = []
    for mapping_name in sorted(stats.keys()):
        data = stats[mapping_name]
        
        # 计算指标
        avg_coverage = (data['total_sensitive_calls'] / bamboo_total * 100) if bamboo_total > 0 else 0
        avg_unique_apis = data['total_unique_apis'] / data['total_apps'] if data['total_apps'] > 0 else 0
        
        coverage_table.append({
            'mapping': mapping_name,
            'total_sensitive_calls': data['total_sensitive_calls'],
            'avg_coverage_pct': avg_coverage,
            'avg_unique_apis': avg_unique_apis,
            'total_apps': data['total_apps'],
            'apps_with_sensitive': data['apps_with_sensitive_apis'],
            'benign_apps': data['benign_apps'],
            'malware_apps': data['malware_apps']
        })
    
    print(f"  [OK] Calculated coverage for {len(stats)} mappings")
    
    return coverage_table, stats


def save_csv(coverage_table):
    """保存为 CSV 格式"""
    with open(OUTPUT_CSV, 'w', newline='', encoding='utf-8') as f:
        writer = csv.DictWriter(f, fieldnames=[
            'mapping',
            'total_sensitive_calls',
            'avg_coverage_pct',
            'avg_unique_apis',
            'total_apps',
            'apps_with_sensitive',
            'benign_apps',
            'malware_apps'
        ])
        
        writer.writeheader()
        writer.writerows(coverage_table)
    
    print(f"  [SAVED] CSV: {OUTPUT_CSV}")


def save_json(coverage_table, stats):
    """保存详细的 JSON 报告"""
    output = {
        'summary': coverage_table,
        'detailed_stats': dict(stats)
    }
    
    with open(OUTPUT_JSON, 'w', encoding='utf-8') as f:
        json.dump(output, f, indent=2, ensure_ascii=False)
    
    print(f"  [SAVED] JSON: {OUTPUT_JSON}")


def print_summary(coverage_table):
    """打印覆盖率摘要"""
    print("\n" + "=" * 80)
    print("Coverage Statistics Summary")
    print("=" * 80)
    
    # 按覆盖率排序
    sorted_table = sorted(coverage_table, key=lambda x: x['avg_coverage_pct'], reverse=True)
    
    print(f"\n{'Mapping':<12} {'Coverage':<12} {'Sensitive Calls':<18} {'Avg APIs/App':<15} {'Apps'}")
    print("-" * 80)
    
    for row in sorted_table:
        print(f"{row['mapping']:<12} "
              f"{row['avg_coverage_pct']:>6.1f}%     "
              f"{row['total_sensitive_calls']:>15,}   "
              f"{row['avg_unique_apis']:>12.1f}   "
              f"{row['total_apps']:>6}")
    
    print("=" * 80)


def main():
    print("=" * 80)
    print("Coverage Calculator")
    print("=" * 80)
    
    # 检查输入目录
    if not MATCHED_DIR.exists() or not list(MATCHED_DIR.glob("*.json")):
        print(f"\n[ERROR] No matched results found in {MATCHED_DIR}")
        print("Please run: python 3_match_mappings.py")
        return
    
    # 1. 加载匹配结果
    results = load_matched_results()
    
    # 2. 计算覆盖率
    coverage_table, stats = calculate_coverage(results)
    
    # 3. 保存结果
    print("\n[Save] Saving results...")
    save_csv(coverage_table)
    save_json(coverage_table, stats)
    
    # 4. 打印摘要
    print_summary(coverage_table)
    
    print("\n[Next Step]")
    print("python 5_detailed_statistics.py")


if __name__ == "__main__":
    main()

