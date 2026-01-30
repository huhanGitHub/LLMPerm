#!/usr/bin/env python3
"""
实验2：Security Risk Assessment

基于sensitive API usage patterns评估app的安全风险等级。
评估不同API-permission mappings对风险评估准确性的影响。

输入:
- ../results/matched_mappings/*.json (包含每个app的sensitive API calls)
- ../mappings/*.json (mapping文件，用于理解API-permission关系)

输出:
- ../results/security_risk_assessment.json
- ../results/security_risk_stats.csv
- ../results/security_risk_table.tex (LaTeX表格)

运行: python 8_security_risk_assessment.py
"""

import json
import csv
import numpy as np
from pathlib import Path
from collections import defaultdict
from scipy import stats
from tqdm import tqdm

# 路径配置
BASE_DIR = Path(__file__).parent.parent
MATCHED_DIR = BASE_DIR / "results" / "matched_mappings"
MAPPINGS_DIR = BASE_DIR / "mappings"
OUTPUT_JSON = BASE_DIR / "results" / "security_risk_assessment.json"
OUTPUT_CSV = BASE_DIR / "results" / "security_risk_stats.csv"
OUTPUT_TEX = BASE_DIR / "results" / "security_risk_table.tex"

# 风险模型定义：基于Permission类型的风险权重
# 参考：Android官方permission危险级别 + 实际安全影响
RISK_WEIGHTS = {
    # High Risk (权重 3.0) - 核心隐私和敏感操作
    "android.permission.ACCESS_FINE_LOCATION": 3.0,
    "android.permission.ACCESS_COARSE_LOCATION": 3.0,
    "android.permission.CAMERA": 3.0,
    "android.permission.RECORD_AUDIO": 3.0,
    "android.permission.READ_CONTACTS": 3.0,
    "android.permission.WRITE_CONTACTS": 3.0,
    "android.permission.READ_SMS": 3.0,
    "android.permission.SEND_SMS": 3.0,
    "android.permission.RECEIVE_SMS": 3.0,
    "android.permission.READ_PHONE_STATE": 3.0,
    "android.permission.CALL_PHONE": 3.0,
    "android.permission.READ_CALL_LOG": 3.0,
    "android.permission.WRITE_CALL_LOG": 3.0,
    "android.permission.READ_EXTERNAL_STORAGE": 3.0,
    "android.permission.WRITE_EXTERNAL_STORAGE": 3.0,
    
    # Medium-High Risk (权重 2.5) - 重要系统操作
    "android.permission.ACCESS_WIFI_STATE": 2.5,
    "android.permission.CHANGE_WIFI_STATE": 2.5,
    "android.permission.BLUETOOTH": 2.5,
    "android.permission.BLUETOOTH_ADMIN": 2.5,
    "android.permission.NFC": 2.5,
    "android.permission.MODIFY_AUDIO_SETTINGS": 2.5,
    "android.permission.VIBRATE": 2.5,
    "android.permission.WAKE_LOCK": 2.5,
    
    # Medium Risk (权重 2.0) - 一般系统访问
    "android.permission.INTERNET": 2.0,
    "android.permission.ACCESS_NETWORK_STATE": 2.0,
    "android.permission.GET_ACCOUNTS": 2.0,
    "android.permission.USE_CREDENTIALS": 2.0,
    "android.permission.MANAGE_ACCOUNTS": 2.0,
    "android.permission.AUTHENTICATE_ACCOUNTS": 2.0,
    "android.permission.READ_SYNC_SETTINGS": 2.0,
    "android.permission.WRITE_SYNC_SETTINGS": 2.0,
    
    # Low-Medium Risk (权重 1.5) - 系统信息访问
    "android.permission.GET_TASKS": 1.5,
    "android.permission.REORDER_TASKS": 1.5,
    "android.permission.GET_PACKAGE_SIZE": 1.5,
    "android.permission.RESTART_PACKAGES": 1.5,
    "android.permission.KILL_BACKGROUND_PROCESSES": 1.5,
    
    # Low Risk (权重 1.0) - 基础权限
    "android.permission.RECEIVE_BOOT_COMPLETED": 1.0,
    "android.permission.SYSTEM_ALERT_WINDOW": 1.0,
    "android.permission.ACCESS_NOTIFICATION_POLICY": 1.0,
    
    # Unknown权限默认权重
    "UNKNOWN": 2.0,  # 未知权限给予中等风险
}

# 风险等级阈值
RISK_LEVEL_THRESHOLDS = {
    "LOW": 5.0,
    "MEDIUM": 15.0,
    "HIGH": float('inf')
}


def load_matched_results():
    """加载所有匹配结果"""
    print("[Load] Loading matched results...")
    
    results = []
    matched_files = list(MATCHED_DIR.glob("*.json"))
    
    for file in matched_files:
        if file.name == "matching_summary.json":
            continue
            
        with open(file, 'r', encoding='utf-8') as f:
            data = json.load(f)
            if 'mappings' in data:
                results.append(data)
    
    print(f"  [OK] Loaded {len(results)} results")
    return results


def get_permission_risk_weight(permission):
    """获取permission的风险权重"""
    # 直接查找
    if permission in RISK_WEIGHTS:
        return RISK_WEIGHTS[permission]
    
    # 模糊匹配（处理一些变体）
    permission_lower = permission.lower()
    for perm_key, weight in RISK_WEIGHTS.items():
        if perm_key.lower() in permission_lower or permission_lower in perm_key.lower():
            return weight
    
    # 默认返回UNKNOWN的权重
    return RISK_WEIGHTS["UNKNOWN"]


def calculate_app_risk_score(mapping_data):
    """
    计算单个app的风险分数
    
    Risk Score = Σ(API calls × permission risk weight)
    
    Args:
        mapping_data: 一个mapping的匹配数据
        
    Returns:
        float: 风险分数
    """
    total_risk = 0.0
    
    # 遍历所有matched APIs
    for api_entry in mapping_data.get('matched_apis', []):
        api_calls = api_entry.get('count', 0)
        permissions = api_entry.get('permissions', [])
        
        if not permissions:
            continue
        
        # 对于每个API调用，计算其风险贡献
        # 如果一个API需要多个permissions，取最高权重
        max_perm_weight = 0.0
        for perm in permissions:
            weight = get_permission_risk_weight(perm)
            max_perm_weight = max(max_perm_weight, weight)
        
        # 风险分数 = API调用次数 × 最高permission权重
        total_risk += api_calls * max_perm_weight
    
    return total_risk


def classify_risk_level(risk_score):
    """根据风险分数分类风险等级"""
    if risk_score < RISK_LEVEL_THRESHOLDS["LOW"]:
        return "LOW"
    elif risk_score < RISK_LEVEL_THRESHOLDS["MEDIUM"]:
        return "MEDIUM"
    else:
        return "HIGH"


def calculate_risk_statistics(results):
    """计算风险评估统计"""
    print("\n[Calculate] Computing security risk statistics...")
    
    # 存储每个mapping的风险数据
    risk_data = defaultdict(lambda: {
        'benign_scores': [],
        'malware_scores': [],
        'all_scores': [],
        'risk_levels': defaultdict(lambda: {'benign': 0, 'malware': 0, 'total': 0})
    })
    
    # 处理每个app
    for result in tqdm(results, desc="Processing apps"):
        is_malware = result.get('is_malware', False)
        
        for mapping_name, mapping_data in result.get('mappings', {}).items():
            # 计算风险分数
            risk_score = calculate_app_risk_score(mapping_data)
            
            # 分类风险等级
            risk_level = classify_risk_level(risk_score)
            
            # 存储数据
            if is_malware:
                risk_data[mapping_name]['malware_scores'].append(risk_score)
                risk_data[mapping_name]['risk_levels'][risk_level]['malware'] += 1
            else:
                risk_data[mapping_name]['benign_scores'].append(risk_score)
                risk_data[mapping_name]['risk_levels'][risk_level]['benign'] += 1
            
            risk_data[mapping_name]['all_scores'].append(risk_score)
            risk_data[mapping_name]['risk_levels'][risk_level]['total'] += 1
    
    # 计算统计指标
    statistics = {}
    
    for mapping_name, data in risk_data.items():
        benign_scores = np.array(data['benign_scores'])
        malware_scores = np.array(data['malware_scores'])
        
        if len(benign_scores) == 0 or len(malware_scores) == 0:
            continue
        
        # 基础统计
        benign_mean = np.mean(benign_scores)
        benign_std = np.std(benign_scores)
        malware_mean = np.mean(malware_scores)
        malware_std = np.std(malware_scores)
        
        # Effect Size (Cohen's d)
        pooled_std = np.sqrt((benign_std**2 + malware_std**2) / 2)
        if pooled_std > 0:
            cohens_d = (benign_mean - malware_mean) / pooled_std
        else:
            cohens_d = 0.0
        
        # 统计显著性测试 (t-test)
        t_stat, p_value = stats.ttest_ind(benign_scores, malware_scores)
        
        # 风险等级分布
        risk_level_dist = {}
        for level in ['LOW', 'MEDIUM', 'HIGH']:
            level_data = data['risk_levels'].get(level, {'benign': 0, 'malware': 0, 'total': 0})
            risk_level_dist[level] = {
                'benign': level_data['benign'],
                'malware': level_data['malware'],
                'total': level_data['total']
            }
        
        statistics[mapping_name] = {
            'benign': {
                'count': len(benign_scores),
                'mean': float(benign_mean),
                'std': float(benign_std),
                'median': float(np.median(benign_scores)),
                'min': float(np.min(benign_scores)),
                'max': float(np.max(benign_scores))
            },
            'malware': {
                'count': len(malware_scores),
                'mean': float(malware_mean),
                'std': float(malware_std),
                'median': float(np.median(malware_scores)),
                'min': float(np.min(malware_scores)),
                'max': float(np.max(malware_scores))
            },
            'effect_size': {
                'cohens_d': float(cohens_d),
                'interpretation': interpret_effect_size(abs(cohens_d))
            },
            'statistical_test': {
                't_statistic': float(t_stat),
                'p_value': float(p_value),
                'significant': p_value < 0.05
            },
            'risk_level_distribution': risk_level_dist
        }
    
    print(f"  [OK] Calculated statistics for {len(statistics)} mappings")
    return statistics, risk_data


def interpret_effect_size(d):
    """解释Effect Size (Cohen's d)"""
    if d < 0.2:
        return "negligible"
    elif d < 0.5:
        return "small"
    elif d < 0.8:
        return "medium"
    else:
        return "large"


def save_results(statistics, risk_data):
    """保存结果到JSON"""
    # 处理不可序列化的值
    thresholds_serializable = {
        k: (v if v != float('inf') else 'inf') 
        for k, v in RISK_LEVEL_THRESHOLDS.items()
    }
    
    output = {
        'risk_model': {
            'weights': RISK_WEIGHTS,
            'thresholds': thresholds_serializable
        },
        'statistics': statistics,
        'raw_data_summary': {
            mapping_name: {
                'benign_count': len(data['benign_scores']),
                'malware_count': len(data['malware_scores']),
                'total_count': len(data['all_scores'])
            }
            for mapping_name, data in risk_data.items()
        }
    }
    
    # 自定义JSON编码器处理numpy类型和bool
    class JSONEncoder(json.JSONEncoder):
        def default(self, obj):
            if isinstance(obj, (np.integer, np.int64, np.int32)):
                return int(obj)
            elif isinstance(obj, (np.floating, np.float64, np.float32)):
                return float(obj)
            elif isinstance(obj, np.bool_):
                return bool(obj)
            elif isinstance(obj, np.ndarray):
                return obj.tolist()
            return super().default(obj)
    
    with open(OUTPUT_JSON, 'w', encoding='utf-8') as f:
        json.dump(output, f, indent=2, ensure_ascii=False, cls=JSONEncoder)
    
    print(f"  [SAVED] JSON: {OUTPUT_JSON}")


def save_csv(statistics):
    """保存统计结果为CSV"""
    rows = []
    
    for mapping_name, stats in statistics.items():
        rows.append({
            'mapping': mapping_name,
            'benign_count': stats['benign']['count'],
            'benign_mean': stats['benign']['mean'],
            'benign_std': stats['benign']['std'],
            'malware_count': stats['malware']['count'],
            'malware_mean': stats['malware']['mean'],
            'malware_std': stats['malware']['std'],
            'cohens_d': stats['effect_size']['cohens_d'],
            'effect_size': stats['effect_size']['interpretation'],
            't_statistic': stats['statistical_test']['t_statistic'],
            'p_value': stats['statistical_test']['p_value'],
            'significant': stats['statistical_test']['significant']
        })
    
    with open(OUTPUT_CSV, 'w', newline='', encoding='utf-8') as f:
        writer = csv.DictWriter(f, fieldnames=rows[0].keys())
        writer.writeheader()
        writer.writerows(rows)
    
    print(f"  [SAVED] CSV: {OUTPUT_CSV}")


def generate_latex_table(statistics):
    """生成LaTeX表格"""
    lines = []
    lines.append("% Security Risk Assessment Results")
    lines.append("% Generated by: 8_security_risk_assessment.py")
    lines.append("")
    lines.append("\\begin{table}[htbp]")
    lines.append("\\centering")
    lines.append("\\caption{Security Risk Assessment Results}")
    lines.append("\\label{tab:security_risk_assessment}")
    lines.append("\\begin{tabular}{lcccccc}")
    lines.append("\\toprule")
    lines.append("Mapping & \\multicolumn{2}{c}{Benign Apps} & \\multicolumn{2}{c}{Malware Apps} & Effect Size & Cohen's d \\\\")
    lines.append("\\cmidrule(lr){2-3} \\cmidrule(lr){4-5}")
    lines.append(" & Mean & Std & Mean & Std & & \\\\")
    lines.append("\\midrule")
    
    # 按mapping名称排序
    mapping_order = ['bamboo', 'dynamo', 'sdk_annotations']
    
    for mapping_name in mapping_order:
        if mapping_name not in statistics:
            continue
        
        stats = statistics[mapping_name]
        
        # 格式化数据
        benign_mean = f"{stats['benign']['mean']:.2f}"
        benign_std = f"{stats['benign']['std']:.2f}"
        malware_mean = f"{stats['malware']['mean']:.2f}"
        malware_std = f"{stats['malware']['std']:.2f}"
        cohens_d = f"{stats['effect_size']['cohens_d']:.2f}"
        effect_interpretation = stats['effect_size']['interpretation'].capitalize()
        
        # Mapping名称格式化
        if mapping_name == 'bamboo':
            mapping_display = "\\tool{}"
        elif mapping_name == 'dynamo':
            mapping_display = "Dynamo"
        elif mapping_name == 'sdk_annotations':
            mapping_display = "SDK Annotations"
        else:
            mapping_display = mapping_name.replace('_', ' ').title()
        
        lines.append(f"{mapping_display} & {benign_mean} & {benign_std} & {malware_mean} & {malware_std} & {effect_interpretation} & {cohens_d} \\\\")
    
    lines.append("\\bottomrule")
    lines.append("\\end{tabular}")
    lines.append("\\end{table}")
    
    with open(OUTPUT_TEX, 'w', encoding='utf-8') as f:
        f.write('\n'.join(lines))
    
    print(f"  [SAVED] LaTeX: {OUTPUT_TEX}")


def print_summary(statistics):
    """打印统计摘要"""
    print("\n" + "=" * 80)
    print("Security Risk Assessment Summary")
    print("=" * 80)
    
    mapping_order = ['bamboo', 'dynamo', 'sdk_annotations']
    
    for mapping_name in mapping_order:
        if mapping_name not in statistics:
            continue
        
        stats = statistics[mapping_name]
        
        print(f"\n[{mapping_name.upper()}]")
        print(f"  Benign Apps (n={stats['benign']['count']}):")
        print(f"    Mean Risk Score: {stats['benign']['mean']:.2f} ± {stats['benign']['std']:.2f}")
        print(f"    Range: [{stats['benign']['min']:.2f}, {stats['benign']['max']:.2f}]")
        print(f"  Malware Apps (n={stats['malware']['count']}):")
        print(f"    Mean Risk Score: {stats['malware']['mean']:.2f} ± {stats['malware']['std']:.2f}")
        print(f"    Range: [{stats['malware']['min']:.2f}, {stats['malware']['max']:.2f}]")
        print(f"  Effect Size:")
        print(f"    Cohen's d: {stats['effect_size']['cohens_d']:.2f} ({stats['effect_size']['interpretation']})")
        print(f"  Statistical Test:")
        print(f"    t-statistic: {stats['statistical_test']['t_statistic']:.2f}")
        print(f"    p-value: {stats['statistical_test']['p_value']:.4f}")
        print(f"    Significant: {'Yes' if stats['statistical_test']['significant'] else 'No'}")
        
        # 风险等级分布
        print(f"  Risk Level Distribution:")
        for level in ['LOW', 'MEDIUM', 'HIGH']:
            level_data = stats['risk_level_distribution'][level]
            print(f"    {level}: Benign={level_data['benign']}, Malware={level_data['malware']}, Total={level_data['total']}")


def main():
    """主函数"""
    print("=" * 80)
    print("Experiment 2: Security Risk Assessment")
    print("=" * 80)
    
    # 1. 加载数据
    results = load_matched_results()
    
    # 2. 计算风险统计
    statistics, risk_data = calculate_risk_statistics(results)
    
    # 3. 保存结果
    save_results(statistics, risk_data)
    save_csv(statistics)
    generate_latex_table(statistics)
    
    # 4. 打印摘要
    print_summary(statistics)
    
    print("\n" + "=" * 80)
    print("[DONE] Security Risk Assessment Complete")
    print("=" * 80)


if __name__ == "__main__":
    main()
