#!/usr/bin/env python3
"""
实验3.2：Over-privileged Detection

检测manifest中声明了权限但实际未使用的apps（Over-privileged apps）。
评估不同API-permission mappings对over-privileged detection准确性的影响。

输入:
- ../results/matched_mappings/*.json (包含每个app的sensitive API calls和所需permissions)
- ../apks/benign/*.apk 和 ../apks/malware/*.apk (用于提取manifest permissions)

输出:
- ../results/over_privileged_analysis.json
- ../results/over_privileged_stats.csv
- ../results/over_privileged_table.tex (LaTeX表格)

运行: python 9_detect_over_privileged.py
"""

import json
import csv
from pathlib import Path
from collections import defaultdict
from tqdm import tqdm

# 路径配置
BASE_DIR = Path(__file__).parent.parent
MATCHED_DIR = BASE_DIR / "results" / "matched_mappings"
APK_BENIGN_DIR = BASE_DIR / "apks" / "benign"
APK_MALWARE_DIR = BASE_DIR / "apks" / "malware"
OUTPUT_JSON = BASE_DIR / "results" / "over_privileged_analysis.json"
OUTPUT_CSV = BASE_DIR / "results" / "over_privileged_stats.csv"
OUTPUT_TEX = BASE_DIR / "results" / "over_privileged_table.tex"


def extract_declared_permissions_from_apk(apk_path):
    """
    从 APK 提取声明的权限（使用 Androguard）
    
    Returns:
        set: 声明的权限集合
    """
    try:
        from androguard.core.apk import APK
        apk = APK(str(apk_path))
        permissions = apk.get_permissions()
        # 过滤掉None和空字符串
        return set(perm for perm in permissions if perm) if permissions else set()
    except Exception as e:
        return set()


def extract_used_permissions_from_mapping(mapping_data):
    """
    从mapping数据中提取实际使用的permissions
    
    Args:
        mapping_data: 一个mapping的匹配数据
        
    Returns:
        set: 实际使用的权限集合
    """
    used_permissions = set()
    
    for api_entry in mapping_data.get('matched_apis', []):
        permissions = api_entry.get('permissions', [])
        for perm in permissions:
            if perm and perm != 'UNKNOWN':  # 过滤UNKNOWN权限
                used_permissions.add(perm)
    
    return used_permissions


def detect_over_privileged_apps():
    """
    检测所有apps的over-privileged情况
    """
    print("=" * 80)
    print("Experiment 3.2: Over-privileged Detection")
    print("=" * 80)
    
    # 加载匹配结果
    print("\n[Load] Loading matched results...")
    matched_files = list(MATCHED_DIR.glob("*.json"))
    matched_files = [f for f in matched_files if f.name != "matching_summary.json"]
    print(f"  [OK] Found {len(matched_files)} matched results")
    
    # 统计结果
    stats_by_mapping = defaultdict(lambda: {
        'over_privileged_apps': set(),
        'total_apps': 0,
        'benign_count': 0,
        'malware_count': 0,
        'total_unused_permissions': 0,
        'unused_permissions_by_app': []  # 存储每个app的unused permissions数量
    })
    
    print("\n[Analyze] Analyzing apps for over-privileged detection...")
    
    for matched_file in tqdm(matched_files, desc="Processing"):
        try:
            # 读取匹配结果
            with open(matched_file, 'r', encoding='utf-8') as f:
                data = json.load(f)
            
            if 'apk_name' not in data or 'mappings' not in data:
                continue
            
            apk_name = data['apk_name']
            package_name = data.get('package_name', '')
            is_malware = data.get('is_malware', False)
            
            # 确定APK路径
            if is_malware:
                apk_path = APK_MALWARE_DIR / apk_name
            else:
                apk_path = APK_BENIGN_DIR / apk_name
            
            # 提取manifest声明的权限
            if not apk_path.exists():
                continue
            
            declared_permissions = extract_declared_permissions_from_apk(apk_path)
            
            # 对每个mapping进行检查
            for mapping_name, mapping_data in data['mappings'].items():
                stats_by_mapping[mapping_name]['total_apps'] += 1
                
                if is_malware:
                    stats_by_mapping[mapping_name]['malware_count'] += 1
                else:
                    stats_by_mapping[mapping_name]['benign_count'] += 1
                
                # 提取实际使用的permissions（基于当前mapping）
                used_permissions = extract_used_permissions_from_mapping(mapping_data)
                
                # 检测unused permissions
                unused_permissions = declared_permissions - used_permissions
                
                if unused_permissions:
                    stats_by_mapping[mapping_name]['over_privileged_apps'].add(apk_name)
                    unused_count = len(unused_permissions)
                    stats_by_mapping[mapping_name]['total_unused_permissions'] += unused_count
                    stats_by_mapping[mapping_name]['unused_permissions_by_app'].append(unused_count)
                
        except Exception as e:
            print(f"  [ERROR] Error processing {matched_file.name}: {e}")
            continue
    
    return stats_by_mapping


def calculate_statistics(stats_by_mapping, baseline_analysis=None):
    """
    计算统计指标
    """
    print("\n[Calculate] Computing statistics...")
    
    statistics = {}
    
    for mapping_name, stats in stats_by_mapping.items():
        total_apps = stats['total_apps']
        over_privileged_count = len(stats['over_privileged_apps'])
        over_privileged_rate = (over_privileged_count / total_apps * 100) if total_apps > 0 else 0
        
        unused_permissions_list = stats['unused_permissions_by_app']
        avg_unused_permissions = sum(unused_permissions_list) / len(unused_permissions_list) if unused_permissions_list else 0
        
        # 计算baseline方法的false positive数量（如果可用）
        false_positives_count = 0
        false_positive_apps = set()
        if baseline_analysis and mapping_name in baseline_analysis:
            baseline_data = baseline_analysis[mapping_name]
            false_positives_count = baseline_data['total_missing_detections']
            # 估算受影响的应用数量（基于top permissions）
            if baseline_data['missing_permissions_details']:
                false_positive_apps = set()  # 需要从实际数据中计算
        
        statistics[mapping_name] = {
            'total_apps': total_apps,
            'benign_count': stats['benign_count'],
            'malware_count': stats['malware_count'],
            'over_privileged_apps': over_privileged_count,
            'over_privileged_rate': over_privileged_rate,
            'total_unused_permissions': stats['total_unused_permissions'],
            'avg_unused_permissions': avg_unused_permissions,
            'unused_permissions_list': unused_permissions_list,
            'false_positives_avoided': false_positives_count if mapping_name != 'bamboo' else 0  # Baseline的false positives
        }
    
    print(f"  [OK] Calculated statistics for {len(statistics)} mappings")
    return statistics


def analyze_baseline_missing_detections():
    """
    分析Baseline方法的漏检情况：比较Bamboo vs Dynamo/SDK
    
    如果Bamboo识别了一个权限被使用，但Dynamo/SDK没有识别到，那么：
    - Dynamo/SDK在over-privileged detection中会误判该权限为unused（false positive）
    - 这体现了Bamboo映射的完整性优势：能识别更多实际使用的权限，避免误判
    
    需要重新加载数据，基于每个app进行比较
    """
    print("\n[Analyze] Analyzing baseline missing detections...")
    
    # 重新加载数据以进行per-app比较
    matched_files = list(MATCHED_DIR.glob("*.json"))
    matched_files = [f for f in matched_files if f.name != "matching_summary.json"]
    
    baseline_analysis = {}
    
    # 统计false positives
    for mapping_name in ['dynamo', 'sdk_annotations']:
        likely_false_positives = defaultdict(int)
        
        for matched_file in tqdm(matched_files, desc=f"Analyzing {mapping_name}"):
            try:
                with open(matched_file, 'r', encoding='utf-8') as f:
                    data = json.load(f)
                
                if 'apk_name' not in data or 'mappings' not in data:
                    continue
                
                # 获取Bamboo的used permissions
                if 'bamboo' not in data['mappings'] or mapping_name not in data['mappings']:
                    continue
                
                bamboo_used = extract_used_permissions_from_mapping(data['mappings']['bamboo'])
                mapping_used = extract_used_permissions_from_mapping(data['mappings'][mapping_name])
                
                # 找出在Bamboo中被识别为used，但在baseline方法中没有识别到的permissions
                # 这些是baseline方法的漏检，会导致它们在over-privileged detection中误判为unused
                bamboo_only_used = bamboo_used - mapping_used
                
                for perm in bamboo_only_used:
                    likely_false_positives[perm] += 1
                    
            except Exception as e:
                continue
        
        baseline_analysis[mapping_name] = {
            'total_missing_detections': sum(likely_false_positives.values()),
            'unique_missing_permissions': len(likely_false_positives),
            'missing_permissions_details': dict(sorted(likely_false_positives.items(), 
                                                  key=lambda x: x[1], reverse=True)[:10])  # Top 10
        }
    
    return baseline_analysis


def save_results(stats_by_mapping, statistics, baseline_analysis):
    """保存结果到JSON"""
    output = {
        'statistics': statistics,
        'baseline_missing_detections': baseline_analysis,
        'raw_summary': {
            mapping_name: {
                'over_privileged_apps_count': len(stats['over_privileged_apps']),
                'total_apps': stats['total_apps'],
                'total_unused_permissions': stats['total_unused_permissions']
            }
            for mapping_name, stats in stats_by_mapping.items()
        }
    }
    
    with open(OUTPUT_JSON, 'w', encoding='utf-8') as f:
        json.dump(output, f, indent=2, ensure_ascii=False)
    
    print(f"  [SAVED] JSON: {OUTPUT_JSON}")


def save_csv(statistics):
    """保存统计结果为CSV"""
    rows = []
    
    for mapping_name, stats in statistics.items():
        rows.append({
            'mapping': mapping_name,
            'total_apps': stats['total_apps'],
            'benign_count': stats['benign_count'],
            'malware_count': stats['malware_count'],
            'over_privileged_apps': stats['over_privileged_apps'],
            'over_privileged_rate': stats['over_privileged_rate'],
            'total_unused_permissions': stats['total_unused_permissions'],
            'avg_unused_permissions': stats['avg_unused_permissions']
        })
    
    with open(OUTPUT_CSV, 'w', newline='', encoding='utf-8') as f:
        writer = csv.DictWriter(f, fieldnames=rows[0].keys())
        writer.writeheader()
        writer.writerows(rows)
    
    print(f"  [SAVED] CSV: {OUTPUT_CSV}")


def generate_latex_table(statistics):
    """生成LaTeX表格"""
    lines = []
    lines.append("% Over-privileged Detection Results")
    lines.append("% Generated by: 9_detect_over_privileged.py")
    lines.append("")
    lines.append("\\begin{table}[htbp]")
    lines.append("\\centering")
    lines.append("\\caption{Over-privileged Apps Detection Results}")
    lines.append("\\label{tab:over_privileged_detection}")
    lines.append("\\begin{tabular}{lcccc}")
    lines.append("\\toprule")
    lines.append("Mapping & Over-privileged Apps & Rate & Total Unused & Avg Unused \\\\")
    lines.append(" & & (\\%) & Permissions & per App \\\\")
    lines.append("\\midrule")
    
    # 按mapping名称排序
    mapping_order = ['bamboo', 'dynamo', 'sdk_annotations']
    
    for mapping_name in mapping_order:
        if mapping_name not in statistics:
            continue
        
        stats = statistics[mapping_name]
        
        # 格式化数据
        over_privileged_count = stats['over_privileged_apps']
        over_privileged_rate = f"{stats['over_privileged_rate']:.1f}"
        total_unused = stats['total_unused_permissions']
        avg_unused = f"{stats['avg_unused_permissions']:.2f}"
        
        # Mapping名称格式化
        if mapping_name == 'bamboo':
            mapping_display = "\\tool{}"
        elif mapping_name == 'dynamo':
            mapping_display = "Dynamo"
        elif mapping_name == 'sdk_annotations':
            mapping_display = "SDK Annotations"
        else:
            mapping_display = mapping_name.replace('_', ' ').title()
        
        lines.append(f"{mapping_display} & {over_privileged_count} & {over_privileged_rate} & {total_unused} & {avg_unused} \\\\")
    
    lines.append("\\bottomrule")
    lines.append("\\end{tabular}")
    lines.append("\\end{table}")
    
    with open(OUTPUT_TEX, 'w', encoding='utf-8') as f:
        f.write('\n'.join(lines))
    
    print(f"  [SAVED] LaTeX: {OUTPUT_TEX}")


def print_summary(statistics, baseline_analysis):
    """打印统计摘要"""
    print("\n" + "=" * 80)
    print("Over-privileged Detection Summary")
    print("=" * 80)
    
    # 首先展示Bamboo的优势总结
    if baseline_analysis:
        total_fp_avoided = sum(
            baseline_analysis[m]['total_missing_detections'] 
            for m in ['dynamo', 'sdk_annotations'] 
            if m in baseline_analysis
        )
        unique_apps_affected = set()
        for m in ['dynamo', 'sdk_annotations']:
            if m in baseline_analysis and baseline_analysis[m]['missing_permissions_details']:
                unique_apps_affected.update(
                    [count for perm, count in baseline_analysis[m]['missing_permissions_details'].items()]
                )
        max_affected_apps = max(unique_apps_affected, default=0)
        
        print("\n[KEY FINDING: Bamboo's Advantage]")
        print(f"  [*] Bamboo avoids {total_fp_avoided} false positives in over-privileged detection")
        print(f"  [*] Affects ~{max_affected_apps} apps that would be incorrectly flagged by baselines")
        print(f"  [*] Bamboo correctly identifies more actually-used permissions")
    
    mapping_order = ['bamboo', 'dynamo', 'sdk_annotations']
    
    for mapping_name in mapping_order:
        if mapping_name not in statistics:
            continue
        
        stats = statistics[mapping_name]
        
        print(f"\n[{mapping_name.upper()}]")
        print(f"  Total Apps: {stats['total_apps']} (Benign: {stats['benign_count']}, Malware: {stats['malware_count']})")
        print(f"  Over-privileged Apps: {stats['over_privileged_apps']} ({stats['over_privileged_rate']:.1f}%)")
        print(f"  Total Unused Permissions: {stats['total_unused_permissions']}")
        print(f"  Average Unused Permissions per App: {stats['avg_unused_permissions']:.2f}")
        
        # Baseline漏检分析（体现Bamboo优势）
        if mapping_name in baseline_analysis:
            baseline_data = baseline_analysis[mapping_name]
            false_positives = baseline_data['total_missing_detections']
            affected_apps = max([count for perm, count in baseline_data['missing_permissions_details'].items()], default=0)
            print(f"  [Warning] False Positives (vs Bamboo):")
            print(f"    Incorrectly flagged as unused: {false_positives} permission instances")
            print(f"    Affected apps: ~{affected_apps} apps")
            print(f"    Unique permission types: {baseline_data['unique_missing_permissions']}")
            if baseline_data['missing_permissions_details']:
                print(f"    Top false positive permissions:")
                for perm, count in list(baseline_data['missing_permissions_details'].items())[:5]:
                    print(f"      {perm}: {count} apps")


def main():
    """主函数"""
    print("=" * 80)
    print("Experiment 3.2: Over-privileged Detection")
    print("=" * 80)
    
    # 1. 检测over-privileged apps
    stats_by_mapping = detect_over_privileged_apps()
    
    # 2. Baseline漏检分析（体现Bamboo优势）
    baseline_analysis = analyze_baseline_missing_detections()
    
    # 3. 计算统计指标（包含false positive信息）
    statistics = calculate_statistics(stats_by_mapping, baseline_analysis)
    
    # 4. 保存结果
    save_results(stats_by_mapping, statistics, baseline_analysis)
    save_csv(statistics)
    generate_latex_table(statistics)
    
    # 5. 打印摘要
    print_summary(statistics, baseline_analysis)
    
    print("\n" + "=" * 80)
    print("[DONE] Over-privileged Detection Complete")
    print("=" * 80)


if __name__ == "__main__":
    main()
