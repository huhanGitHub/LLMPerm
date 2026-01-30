#!/usr/bin/env python3
"""
一键运行所有实验脚本

按顺序运行所有分析步骤，从映射转换到生成 LaTeX 表格。

前置条件:
1. APK 数据集已下载
2. API key 已设置（如果需要下载）

运行: python run_all.py
"""

import subprocess
import sys
from pathlib import Path

# 脚本列表
SCRIPTS = [
    ("1_convert_mappings.py", "Convert mapping files"),
    ("2_extract_api_calls.py", "Extract API calls from APKs"),
    ("3_match_mappings.py", "Match API calls with mappings"),
    ("4_calculate_coverage.py", "Calculate coverage statistics"),
    ("5_detailed_statistics.py", "Generate detailed statistics and charts"),
    ("6_generate_latex_table.py", "Generate LaTeX tables")
]


def run_script(script_name, description):
    """运行单个脚本"""
    print("\n" + "=" * 80)
    print(f"Running: {script_name}")
    print(f"Description: {description}")
    print("=" * 80 + "\n")
    
    try:
        # 运行脚本
        if script_name == "2_extract_api_calls.py":
            # API 提取使用并行处理
            result = subprocess.run(
                [sys.executable, script_name, "--parallel", "8"],
                check=True
            )
        else:
            result = subprocess.run(
                [sys.executable, script_name],
                check=True
            )
        
        print(f"\n[OK] {script_name} completed successfully")
        return True
        
    except subprocess.CalledProcessError as e:
        print(f"\n[ERROR] {script_name} failed with exit code {e.returncode}")
        return False
    except Exception as e:
        print(f"\n[ERROR] {script_name} failed: {e}")
        return False


def main():
    print("=" * 80)
    print("Privacy Leakage Experiment - Complete Pipeline")
    print("=" * 80)
    print(f"\nTotal steps: {len(SCRIPTS)}")
    print("Estimated time: 2-8 hours (depending on APK count)\n")
    
    response = input("Start complete pipeline? (y/n): ")
    if response.lower() != 'y':
        print("Cancelled.")
        return
    
    # 运行所有脚本
    completed = []
    failed = []
    
    for script_name, description in SCRIPTS:
        success = run_script(script_name, description)
        
        if success:
            completed.append(script_name)
        else:
            failed.append(script_name)
            print(f"\n[STOP] Pipeline stopped due to error in {script_name}")
            break
    
    # 总结
    print("\n" + "=" * 80)
    print("Pipeline Summary")
    print("=" * 80)
    print(f"[OK] Completed: {len(completed)}/{len(SCRIPTS)}")
    
    if completed:
        print("\nCompleted scripts:")
        for script in completed:
            print(f"  - {script}")
    
    if failed:
        print("\nFailed scripts:")
        for script in failed:
            print(f"  - {script}")
    
    if len(completed) == len(SCRIPTS):
        print("\n[SUCCESS] All scripts completed!")
        print("Results are ready for paper writing.")
        print("\nCheck:")
        print("  - results/coverage_stats.csv")
        print("  - results/*.png")
        print("  - results/privacy_coverage_table.tex")
    else:
        print("\n[INCOMPLETE] Pipeline did not complete")
        print(f"Resume with: python {SCRIPTS[len(completed)][0]}")


if __name__ == "__main__":
    main()

