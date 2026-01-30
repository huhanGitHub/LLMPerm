#!/usr/bin/env python3
"""
生成详细统计和图表

基于覆盖率数据生成图表和详细分析报告。

输入:
- ../results/coverage_stats.csv
- ../results/matched_mappings/*.json

输出:
- ../results/coverage_distribution.png
- ../results/malware_comparison.png
- ../results/unique_discoveries.txt

运行: python 5_detailed_statistics.py
"""

import json
import csv
from pathlib import Path
import matplotlib
matplotlib.use('Agg')  # 非交互式后端
import matplotlib.pyplot as plt
import numpy as np
from collections import defaultdict

# 路径配置
BASE_DIR = Path(__file__).parent.parent
MATCHED_DIR = BASE_DIR / "results" / "matched_mappings"
CSV_FILE = BASE_DIR / "results" / "coverage_stats.csv"
RESULTS_DIR = BASE_DIR / "results"


def load_matched_results():
    """加载所有匹配结果"""
    results = []
    for file in MATCHED_DIR.glob("*.json"):
        with open(file, 'r', encoding='utf-8') as f:
            results.append(json.load(f))
    return results


def plot_coverage_distribution(results):
    """绘制覆盖率分布图"""
    print("[Plot] Coverage distribution...")
    
    # 收集每个应用在不同映射下的覆盖率
    coverage_data = defaultdict(list)
    
    for result in results:
        bamboo_calls = result['mappings'].get('bamboo', {}).get('sensitive_api_calls', 1)
        
        for mapping_name, data in result['mappings'].items():
            if bamboo_calls > 0:
                coverage = data['sensitive_api_calls'] / bamboo_calls * 100
                coverage_data[mapping_name].append(coverage)
    
    # 绘图
    fig, ax = plt.subplots(figsize=(10, 6))
    
    positions = []
    labels = []
    data_to_plot = []
    
    for i, (mapping_name, coverages) in enumerate(sorted(coverage_data.items())):
        positions.append(i + 1)
        labels.append(mapping_name.capitalize())
        data_to_plot.append(coverages)
    
    bp = ax.boxplot(data_to_plot, positions=positions, labels=labels, patch_artist=True)
    
    # 美化
    for patch in bp['boxes']:
        patch.set_facecolor('lightblue')
    
    ax.set_xlabel('Mapping', fontsize=12)
    ax.set_ylabel('Coverage (%)', fontsize=12)
    ax.set_title('API Coverage Distribution Across Applications', fontsize=14)
    ax.grid(True, alpha=0.3)
    
    output_file = RESULTS_DIR / "coverage_distribution.png"
    plt.tight_layout()
    plt.savefig(output_file, dpi=300)
    plt.close()
    
    print(f"  [SAVED] {output_file}")


def plot_malware_comparison(results):
    """绘制良性 vs 恶意应用的 API 使用对比"""
    print("[Plot] Malware comparison...")
    
    # 收集数据
    mapping_stats = defaultdict(lambda: {'benign': [], 'malware': []})
    
    for result in results:
        is_malware = result['is_malware']
        category = 'malware' if is_malware else 'benign'
        
        for mapping_name, data in result['mappings'].items():
            unique_apis = data['unique_sensitive_apis']
            mapping_stats[mapping_name][category].append(unique_apis)
    
    # 绘图
    fig, ax = plt.subplots(figsize=(10, 6))
    
    mappings = sorted(mapping_stats.keys())
    x = np.arange(len(mappings))
    width = 0.35
    
    benign_means = [np.mean(mapping_stats[m]['benign']) for m in mappings]
    malware_means = [np.mean(mapping_stats[m]['malware']) for m in mappings]
    
    ax.bar(x - width/2, benign_means, width, label='Benign', color='green', alpha=0.7)
    ax.bar(x + width/2, malware_means, width, label='Malware', color='red', alpha=0.7)
    
    ax.set_xlabel('Mapping', fontsize=12)
    ax.set_ylabel('Avg Unique Sensitive APIs', fontsize=12)
    ax.set_title('Sensitive API Usage: Benign vs Malware', fontsize=14)
    ax.set_xticks(x)
    ax.set_xticklabels([m.capitalize() for m in mappings])
    ax.legend()
    ax.grid(True, alpha=0.3, axis='y')
    
    output_file = RESULTS_DIR / "malware_comparison.png"
    plt.tight_layout()
    plt.savefig(output_file, dpi=300)
    plt.close()
    
    print(f"  [SAVED] {output_file}")


def find_unique_discoveries(results):
    """找出 Bamboo 独有发现的 API"""
    print("[Analyze] Finding unique discoveries...")
    
    # 收集所有映射识别的 API
    all_apis = defaultdict(set)
    
    for result in results:
        for mapping_name, data in result['mappings'].items():
            for api in data['matched_apis']:
                signature = api['signature']
                all_apis[mapping_name].add(signature)
    
    # 找出 Bamboo 独有的 API
    bamboo_apis = all_apis.get('bamboo', set())
    dynamo_apis = all_apis.get('dynamo', set())
    arcade_apis = all_apis.get('arcade', set())
    
    # Bamboo 独有（不在任何其他映射中）
    unique_to_bamboo = bamboo_apis - dynamo_apis - arcade_apis
    
    # Bamboo vs Dynamo
    bamboo_only = bamboo_apis - dynamo_apis
    
    # 统计这些 API 的使用频率
    api_usage_count = defaultdict(int)
    for result in results:
        bamboo_data = result['mappings'].get('bamboo', {})
        for api in bamboo_data.get('matched_apis', []):
            if api['signature'] in unique_to_bamboo:
                api_usage_count[api['signature']] += api['count']
    
    # 按使用频率排序
    top_unique = sorted(api_usage_count.items(), key=lambda x: x[1], reverse=True)[:20]
    
    # 保存结果
    output_file = RESULTS_DIR / "unique_discoveries.txt"
    with open(output_file, 'w', encoding='utf-8') as f:
        f.write("# Bamboo Unique API Discoveries\n\n")
        f.write(f"Total unique to Bamboo: {len(unique_to_bamboo)}\n")
        f.write(f"Bamboo-only (vs Dynamo): {len(bamboo_only)}\n\n")
        f.write("Top 20 Most Frequently Used Unique APIs:\n")
        f.write("=" * 80 + "\n\n")
        
        for i, (api, count) in enumerate(top_unique, 1):
            f.write(f"{i:2d}. {api}\n")
            f.write(f"    Usage count: {count}\n\n")
    
    print(f"  [SAVED] {output_file}")
    print(f"  [STAT] Unique to Bamboo: {len(unique_to_bamboo)}")
    print(f"  [STAT] Top unique API: {top_unique[0][0] if top_unique else 'N/A'}")


def generate_comparison_chart(coverage_table):
    """生成映射对比图"""
    print("[Plot] Comparison chart...")
    
    # 读取 CSV
    with open(coverage_table, 'r', encoding='utf-8') as f:
        reader = csv.DictReader(f)
        data = list(reader)
    
    mappings = [row['mapping'] for row in data]
    coverages = [float(row['avg_coverage_pct']) for row in data]
    sensitive_calls = [int(row['total_sensitive_calls']) for row in data]
    
    # 创建双轴图
    fig, (ax1, ax2) = plt.subplots(1, 2, figsize=(14, 5))
    
    # 子图 1: 覆盖率对比
    colors = ['#2ecc71' if m == 'bamboo' else '#3498db' for m in mappings]
    ax1.bar(range(len(mappings)), coverages, color=colors, alpha=0.7)
    ax1.set_xlabel('Mapping', fontsize=11)
    ax1.set_ylabel('Coverage (%)', fontsize=11)
    ax1.set_title('Relative Coverage (Bamboo = 100%)', fontsize=12)
    ax1.set_xticks(range(len(mappings)))
    ax1.set_xticklabels([m.capitalize() for m in mappings])
    ax1.grid(True, alpha=0.3, axis='y')
    
    # 添加数值标签
    for i, (m, c) in enumerate(zip(mappings, coverages)):
        ax1.text(i, c + 2, f'{c:.1f}%', ha='center', fontsize=10)
    
    # 子图 2: 绝对数量对比
    ax2.bar(range(len(mappings)), sensitive_calls, color=colors, alpha=0.7)
    ax2.set_xlabel('Mapping', fontsize=11)
    ax2.set_ylabel('Total Sensitive API Calls', fontsize=11)
    ax2.set_title('Absolute Number of Sensitive API Calls', fontsize=12)
    ax2.set_xticks(range(len(mappings)))
    ax2.set_xticklabels([m.capitalize() for m in mappings])
    ax2.grid(True, alpha=0.3, axis='y')
    
    # 添加数值标签
    for i, (m, s) in enumerate(zip(mappings, sensitive_calls)):
        ax2.text(i, s + max(sensitive_calls)*0.02, f'{s:,}', ha='center', fontsize=10)
    
    output_file = RESULTS_DIR / "coverage_comparison.png"
    plt.tight_layout()
    plt.savefig(output_file, dpi=300)
    plt.close()
    
    print(f"  [SAVED] {output_file}")


def main():
    print("=" * 80)
    print("Detailed Statistics Generator")
    print("=" * 80)
    
    # 检查输入
    if not MATCHED_DIR.exists() or not list(MATCHED_DIR.glob("*.json")):
        print(f"\n[ERROR] No matched results found")
        print("Please run: python 3_match_mappings.py")
        return
    
    # 1. 加载数据
    results = load_matched_results()
    
    # 2. 计算覆盖率
    coverage_table, stats = calculate_coverage(results)
    
    # 3. 保存结果
    print("\n[Save] Saving results...")
    save_csv(coverage_table)
    save_json(coverage_table, stats)
    
    # 4. 生成图表
    print("\n[Generate] Creating visualizations...")
    try:
        plot_coverage_distribution(results)
        plot_malware_comparison(results)
        generate_comparison_chart(CSV_FILE)
    except Exception as e:
        print(f"  [WARN] Plotting failed: {e}")
        print("  [INFO] CSV results are still available")
    
    # 5. 分析独有发现
    print("\n[Analyze] Finding unique discoveries...")
    find_unique_discoveries(results)
    
    # 6. 打印摘要
    print("\n" + "=" * 80)
    print("Statistics Complete!")
    print("=" * 80)
    print(f"[Output] Coverage CSV: {CSV_FILE}")
    print(f"[Output] Details JSON: {OUTPUT_JSON}")
    print(f"[Output] Charts: {RESULTS_DIR}/*.png")
    print(f"[Output] Unique APIs: {RESULTS_DIR}/unique_discoveries.txt")
    
    print("\n[Next Step]")
    print("python 6_generate_latex_table.py")


if __name__ == "__main__":
    main()

