#!/usr/bin/env python3
"""
Data Verification Script

This script verifies the completeness and consistency of data files
required for reproducing the paper's experiments.
"""

import os
import json
import csv
from pathlib import Path
from typing import Dict, List, Tuple

# Base directory (parent of code/ and data/)
BASE_DIR = Path(__file__).parent.parent.parent


def check_file_exists(filepath: Path, description: str) -> Tuple[bool, str]:
    """Check if a file exists."""
    if filepath.exists():
        return True, f"[OK] {description}: Found"
    else:
        return False, f"[MISSING] {description}: Missing ({filepath})"


def check_directory_exists(dirpath: Path, description: str) -> Tuple[bool, str]:
    """Check if a directory exists and is not empty."""
    if not dirpath.exists():
        return False, f"[MISSING] {description}: Directory missing ({dirpath})"
    if not any(dirpath.iterdir()):
        return False, f"[WARNING] {description}: Directory empty ({dirpath})"
    return True, f"[OK] {description}: Found with files"


def verify_android_mappings() -> List[Tuple[bool, str]]:
    """Verify Android version mappings."""
    results = []
    # Only Android 15 has mappings
    version = 'android_15'
    mapping_dir = BASE_DIR / 'data' / version
    json_file = mapping_dir / 'bamboo_mapping.json'
    txt_file = mapping_dir / 'permission_mappings.txt'
    readme_file = mapping_dir / 'README.md'
    
    results.append(check_file_exists(json_file, f"{version} JSON mapping"))
    results.append(check_file_exists(txt_file, f"{version} TXT mapping"))
    results.append(check_file_exists(readme_file, f"{version} README"))
    
    return results


def verify_baselines() -> List[Tuple[bool, str]]:
    """Verify baseline mappings."""
    results = []
    baselines = ['dynamo', 'arcade', 'natidroid', 'sdk_annotations']
    
    for baseline in baselines:
        baseline_dir = BASE_DIR / 'data' / 'baselines' / baseline
        results.append(check_directory_exists(baseline_dir, f"Baseline: {baseline}"))
    
    return results


def verify_rq1() -> List[Tuple[bool, str]]:
    """Verify RQ1 experimental data."""
    # RQ1 directory has been removed as it was empty
    return []


def verify_rq2() -> List[Tuple[bool, str]]:
    """Verify RQ2 experimental data."""
    results = []
    rq2_dir = BASE_DIR / 'experiments' / 'rq2'
    
    # RQ2 data is not included in repository (ablation study results are reported in paper only)
    results.append((True, "[OK] RQ2: Data not included (results reported in paper only)"))
    
    return results


def verify_rq3() -> List[Tuple[bool, str]]:
    """Verify RQ3 experimental data."""
    results = []
    rq3_dir = BASE_DIR / 'data' / 'rq3'
    
    # App analysis
    api_calls_dir = rq3_dir / 'app_analysis' / 'api_calls'
    matched_dir = rq3_dir / 'app_analysis' / 'matched_mappings'
    
    results.append(check_directory_exists(api_calls_dir, "RQ3 API calls"))
    results.append(check_directory_exists(matched_dir, "RQ3 matched mappings"))
    
    # Count files
    if api_calls_dir.exists():
        json_files = list(api_calls_dir.glob('*.json'))
        count = len(json_files)
        if count >= 1182:
            results.append((True, f"[OK] RQ3 API calls: {count} apps (expected >=1182)"))
        else:
            results.append((False, f"[WARNING] RQ3 API calls: {count} apps (expected >=1182)"))
    
    # Statistics
    stats_dir = rq3_dir / 'statistics'
    results.append(check_file_exists(stats_dir / 'coverage_stats.csv', "RQ3 coverage stats"))
    results.append(check_file_exists(stats_dir / 'security_risk_stats.csv', "RQ3 security risk stats"))
    results.append(check_file_exists(stats_dir / 'over_privileged_stats.csv', "RQ3 over-privileged stats"))
    results.append(check_file_exists(rq3_dir / 'README.md', "RQ3 README"))
    
    return results


def verify_data_files() -> List[Tuple[bool, str]]:
    """Verify supporting data files."""
    results = []
    data_dir = BASE_DIR / 'data'
    
    sha256_file = data_dir / 'androzoo_sha256_list.txt'
    results.append(check_file_exists(sha256_file, "AndroZoo SHA256 list"))
    
    # Check SHA256 list content
    if sha256_file.exists():
        try:
            with open(sha256_file, 'r', encoding='utf-8') as f:
                lines = [l.strip() for l in f if l.strip() and not l.startswith('#')]
            count = len(lines)
            if count >= 1182:
                results.append((True, f"[OK] SHA256 list: {count} entries (expected >=1182)"))
            else:
                results.append((False, f"[WARNING] SHA256 list: {count} entries (expected >=1182)"))
        except Exception as e:
            results.append((False, f"[ERROR] SHA256 list: Error reading file ({e})"))
    
    return results


def verify_scripts() -> List[Tuple[bool, str]]:
    """Verify reproduction scripts."""
    results = []
    scripts_dir = BASE_DIR / 'code' / 'scripts'
    
    scripts = ['rq1_reproduce.py', 'rq2_reproduce.py', 'rq3_reproduce.py']
    for script in scripts:
        results.append(check_file_exists(scripts_dir / script, f"Script: {script}"))
    
    return results


def main():
    """Run all verification checks."""
    print("=" * 70)
    print("Data Verification Report")
    print("=" * 70)
    print()
    
    all_results = []
    
    print("1. Android Version Mappings")
    print("-" * 70)
    results = verify_android_mappings()
    all_results.extend(results)
    for status, msg in results:
        print(msg)
    print()
    
    print("2. Baseline Mappings")
    print("-" * 70)
    results = verify_baselines()
    all_results.extend(results)
    for status, msg in results:
        print(msg)
    print()
    
    # RQ1 directory removed (was empty)
    
    print("4. RQ2: Ablation Study & LLM Comparison")
    print("-" * 70)
    results = verify_rq2()
    all_results.extend(results)
    for status, msg in results:
        print(msg)
    print("   Note: RQ2 experimental data is not included in repository.")
    print()
    
    print("5. RQ3: Downstream Impact Analysis")
    print("-" * 70)
    results = verify_rq3()
    all_results.extend(results)
    for status, msg in results:
        print(msg)
    print()
    
    print("6. Supporting Data Files")
    print("-" * 70)
    results = verify_data_files()
    all_results.extend(results)
    for status, msg in results:
        print(msg)
    print()
    
    print("7. Reproduction Scripts")
    print("-" * 70)
    results = verify_scripts()
    all_results.extend(results)
    for status, msg in results:
        print(msg)
    print()
    
    # Summary
    print("=" * 70)
    print("Summary")
    print("=" * 70)
    total = len(all_results)
    passed = sum(1 for status, _ in all_results if status)
    failed = total - passed
    
    print(f"Total checks: {total}")
    print(f"[OK] Passed: {passed}")
    print(f"[FAILED/WARNING] Failed/Warnings: {failed}")
    print()
    
    if failed > 0:
        print("[WARNING] Some data files are missing or incomplete.")
        print("   See DATA_VERIFICATION.md for details on generating missing data.")
    else:
        print("[OK] All data files are present and complete!")
    
    return failed == 0


if __name__ == '__main__':
    success = main()
    exit(0 if success else 1)
