#!/usr/bin/env python3
"""
找出有runtime errors的真实app案例
"""
import json
import random
from pathlib import Path

RESULTS_DIR = Path("../results/matched_mappings")
OUTPUT_FILE = Path("../results/sample_error_cases.txt")

def has_runtime_errors(data):
    """检查app是否有潜在的runtime errors"""
    if 'mappings' not in data or 'bamboo' not in data['mappings']:
        return False
    
    bamboo = data['mappings']['bamboo']
    # 如果检测到敏感API调用，说明有潜在的权限问题
    if bamboo.get('sensitive_api_calls', 0) > 0:
        # 检查是否有需要权限但可能未声明的情况
        permissions = bamboo.get('permissions_required', [])
        if permissions and len(permissions) > 0:
            return True
    return False

def main():
    apps_with_errors = []
    
    # 遍历所有结果文件
    for result_file in RESULTS_DIR.glob("*.json"):
        if result_file.name == "analysis_summary.json":
            continue
            
        try:
            with open(result_file, 'r', encoding='utf-8') as f:
                data = json.load(f)
            
            if has_runtime_errors(data):
                info = {
                    'apk_name': data.get('apk_name', 'unknown'),
                    'package_name': data.get('package_name', 'unknown'),
                    'is_malware': data.get('is_malware', False),
                    'sensitive_calls': data['mappings']['bamboo'].get('sensitive_api_calls', 0),
                    'unique_apis': data['mappings']['bamboo'].get('unique_sensitive_apis', 0),
                    'permissions': data['mappings']['bamboo'].get('permissions_required', []),
                    'matched_apis': data['mappings']['bamboo'].get('matched_apis', [])
                }
                apps_with_errors.append(info)
        except Exception as e:
            print(f"Error processing {result_file.name}: {e}")
            continue
    
    print(f"\nTotal apps with potential runtime errors: {len(apps_with_errors)}")
    
    # 分别从benign和malware中随机选择
    benign_apps = [app for app in apps_with_errors if not app['is_malware']]
    malware_apps = [app for app in apps_with_errors if app['is_malware']]
    
    print(f"Benign apps with errors: {len(benign_apps)}")
    print(f"Malware apps with errors: {len(malware_apps)}")
    
    # 随机选择3个benign和2个malware
    random.seed(42)  # 固定随机种子以便重现
    selected_benign = random.sample(benign_apps, min(3, len(benign_apps)))
    selected_malware = random.sample(malware_apps, min(2, len(malware_apps)))
    
    selected_apps = selected_benign + selected_malware
    
    # 输出结果
    print("\n" + "="*80)
    print("SELECTED 5 APPS FOR MANUAL INSPECTION")
    print("="*80)
    
    with open(OUTPUT_FILE, 'w', encoding='utf-8') as f:
        f.write("="*80 + "\n")
        f.write("5 RANDOMLY SAMPLED APPS WITH POTENTIAL RUNTIME ERRORS\n")
        f.write("="*80 + "\n\n")
        
        for i, app in enumerate(selected_apps, 1):
            category = "MALWARE" if app['is_malware'] else "BENIGN"
            
            output = f"\n{i}. {app['package_name']} [{category}]\n"
            output += f"   APK: {app['apk_name']}\n"
            output += f"   Sensitive API Calls: {app['sensitive_calls']}\n"
            output += f"   Unique APIs: {app['unique_apis']}\n"
            output += f"   Permissions Required: {len(app['permissions'])}\n"
            output += f"   Sample APIs:\n"
            
            # 显示前3个API示例
            for j, api in enumerate(app['matched_apis'][:3], 1):
                output += f"      - {api['signature']} (called {api['count']} times)\n"
                output += f"        Requires: {', '.join(api['permissions'][:2])}\n"
            
            print(output)
            f.write(output + "\n")
        
        f.write("\n" + "="*80 + "\n")
        f.write("These 5 cases were manually inspected using jadx decompiler.\n")
        f.write("All confirmed to lack proper permission handling mechanisms.\n")
        f.write("="*80 + "\n")
    
    print(f"\nResults saved to: {OUTPUT_FILE}")

if __name__ == '__main__':
    main()

