#!/usr/bin/env python3
"""
生成 LaTeX 表格

基于统计结果生成用于论文的 LaTeX 表格。

输入:
- ../results/coverage_stats.csv
- ../results/unique_discoveries.txt

输出:
- ../results/privacy_coverage_table.tex
- ../results/malware_detection_table.tex
- ../results/runtime_errors_table.tex

运行: python 6_generate_latex_table.py
"""

import csv
import json
from pathlib import Path
from collections import defaultdict

# 路径配置
BASE_DIR = Path(__file__).parent.parent
CSV_FILE = BASE_DIR / "results" / "coverage_stats.csv"
MATCHED_DIR = BASE_DIR / "results" / "matched_mappings"
RESULTS_DIR = BASE_DIR / "results"


def generate_privacy_table():
    """生成 Privacy Analysis 表格"""
    print("[Generate] Privacy coverage table...")
    
    # 读取 CSV
    with open(CSV_FILE, 'r', encoding='utf-8') as f:
        reader = csv.DictReader(f)
        data = sorted(list(reader), key=lambda x: float(x['avg_coverage_pct']), reverse=True)
    
    # 生成 LaTeX
    latex = []
    latex.append(r"\begin{table}[t]")
    latex.append(r"\centering")
    latex.append(r"\caption{Privacy Analysis: Coverage of Sensitive API Calls}")
    latex.append(r"\label{tab:privacy_coverage}")
    latex.append(r"\begin{tabular}{lccc}")
    latex.append(r"\toprule")
    latex.append(r"Mapping & Coverage (\%) & Sensitive Calls & Avg APIs/App \\")
    latex.append(r"\midrule")
    
    for row in data:
        mapping = row['mapping'].capitalize()
        if row['mapping'] == 'bamboo':
            mapping = r"\tool{}"  # 使用论文中定义的工具名
        
        coverage = float(row['avg_coverage_pct'])
        calls = int(row['total_sensitive_calls'])
        avg_apis = float(row['avg_unique_apis'])
        
        latex.append(f"{mapping} & {coverage:.1f} & {calls:,} & {avg_apis:.1f} \\\\")
    
    latex.append(r"\bottomrule")
    latex.append(r"\end{tabular}")
    latex.append(r"\end{table}")
    
    # 保存
    output_file = RESULTS_DIR / "privacy_coverage_table.tex"
    with open(output_file, 'w', encoding='utf-8') as f:
        f.write('\n'.join(latex))
    
    print(f"  [SAVED] {output_file}")
    
    return output_file


def generate_malware_detection_table():
    """生成 Security Analysis 表格"""
    print("[Generate] Malware detection table...")
    
    # 加载匹配结果
    benign_stats = defaultdict(list)
    malware_stats = defaultdict(list)
    
    for file in MATCHED_DIR.glob("*.json"):
        with open(file, 'r', encoding='utf-8') as f:
            data = json.load(f)
        
        # 跳过摘要文件
        if 'is_malware' not in data or 'mappings' not in data:
            continue
        
        is_malware = data['is_malware']
        category = malware_stats if is_malware else benign_stats
        
        for mapping_name, mapping_data in data['mappings'].items():
            unique_apis = mapping_data['unique_sensitive_apis']
            category[mapping_name].append(unique_apis)
    
    # 计算统计量
    import numpy as np
    
    latex = []
    latex.append(r"\begin{table}[t]")
    latex.append(r"\centering")
    latex.append(r"\caption{Security Analysis: Malware Detection Capability}")
    latex.append(r"\label{tab:malware_detection}")
    latex.append(r"\begin{tabular}{lcccc}")
    latex.append(r"\toprule")
    latex.append(r"Mapping & Benign Avg & Malware Avg & Difference & Effect Size \\")
    latex.append(r"\midrule")
    
    for mapping_name in sorted(benign_stats.keys()):
        benign_vals = benign_stats[mapping_name]
        malware_vals = malware_stats[mapping_name]
        
        benign_mean = np.mean(benign_vals)
        malware_mean = np.mean(malware_vals)
        difference = malware_mean - benign_mean
        
        # Cohen's d effect size
        pooled_std = np.sqrt((np.std(benign_vals)**2 + np.std(malware_vals)**2) / 2)
        effect_size = difference / pooled_std if pooled_std > 0 else 0
        
        mapping = mapping_name.capitalize()
        if mapping_name == 'bamboo':
            mapping = r"\tool{}"
        
        latex.append(f"{mapping} & {benign_mean:.1f} & {malware_mean:.1f} & "
                    f"{difference:.1f} & {effect_size:.2f} \\\\")
    
    latex.append(r"\bottomrule")
    latex.append(r"\end{tabular}")
    latex.append(r"\end{table}")
    
    # 保存
    output_file = RESULTS_DIR / "malware_detection_table.tex"
    with open(output_file, 'w', encoding='utf-8') as f:
        f.write('\n'.join(latex))
    
    print(f"  [SAVED] {output_file}")
    
    return output_file


def generate_all_tables():
    """生成所有表格的合并版本"""
    print("[Generate] Combined tables...")
    
    output_file = RESULTS_DIR / "all_tables.tex"
    
    latex = []
    latex.append("% Privacy Analysis Table")
    latex.append("% Copy this to your paper's evaluation section")
    latex.append("")
    
    # Privacy table
    with open(RESULTS_DIR / "privacy_coverage_table.tex", 'r') as f:
        latex.append(f.read())
    
    latex.append("")
    latex.append("")
    latex.append("% Security Analysis Table")
    latex.append("")
    
    # Malware detection table
    if (RESULTS_DIR / "malware_detection_table.tex").exists():
        with open(RESULTS_DIR / "malware_detection_table.tex", 'r') as f:
            latex.append(f.read())
    
    # 保存
    with open(output_file, 'w', encoding='utf-8') as f:
        f.write('\n'.join(latex))
    
    print(f"  [SAVED] {output_file}")
    
    return output_file


def main():
    print("=" * 80)
    print("LaTeX Table Generator")
    print("=" * 80)
    
    # 检查输入
    if not CSV_FILE.exists():
        print(f"\n[ERROR] Coverage stats not found: {CSV_FILE}")
        print("Please run: python 4_calculate_coverage.py")
        return
    
    if not MATCHED_DIR.exists() or not list(MATCHED_DIR.glob("*.json")):
        print(f"\n[ERROR] Matched results not found")
        print("Please run: python 3_match_mappings.py")
        return
    
    print()
    
    # 生成表格
    privacy_table = generate_privacy_table()
    malware_table = generate_malware_detection_table()
    combined_table = generate_all_tables()
    
    # 总结
    print("\n" + "=" * 80)
    print("LaTeX Tables Generated!")
    print("=" * 80)
    print(f"[Output] Privacy table: {privacy_table}")
    print(f"[Output] Malware table: {malware_table}")
    print(f"[Output] All tables: {combined_table}")
    
    print("\n[Usage] Copy these tables to your LaTeX paper:")
    print(f"  \\input{{results/privacy_coverage_table.tex}}")
    print(f"  \\input{{results/malware_detection_table.tex}}")
    
    print("\n[Complete] All scripts finished!")
    print("Results are ready for paper writing.")


if __name__ == "__main__":
    main()

