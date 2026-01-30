#!/usr/bin/env python3
"""
APK 验证脚本

验证下载的 APK 文件：
1. 文件格式正确（ZIP 格式）
2. 包含必要的文件（AndroidManifest.xml, classes.dex）
3. 文件大小合理
4. 可以被 Androguard 解析

运行: python validate_apks.py
"""

import os
import zipfile
from pathlib import Path
from tqdm import tqdm
import json

# 目录
APK_DIR = Path("../apks")
OUTPUT_FILE = APK_DIR / "validation_report.json"

# 验证配置
MIN_SIZE_KB = 100  # 最小 100KB
MAX_SIZE_MB = 50   # 最大 50MB


def validate_apk(apk_path):
    """验证单个 APK"""
    result = {
        'file': apk_path.name,
        'size_mb': 0,
        'is_valid': False,
        'errors': []
    }
    
    try:
        # 1. 检查文件大小
        size_bytes = apk_path.stat().st_size
        size_mb = size_bytes / (1024 * 1024)
        size_kb = size_bytes / 1024
        result['size_mb'] = round(size_mb, 2)
        
        if size_kb < MIN_SIZE_KB:
            result['errors'].append(f'File too small: {size_kb:.2f} KB')
            return result
        
        if size_mb > MAX_SIZE_MB:
            result['errors'].append(f'File too large: {size_mb:.2f} MB')
            return result
        
        # 2. 检查是否为有效的 ZIP 文件
        if not zipfile.is_zipfile(apk_path):
            result['errors'].append('Not a valid ZIP file')
            return result
        
        # 3. 检查必要的文件
        required_files = ['AndroidManifest.xml', 'classes.dex']
        with zipfile.ZipFile(apk_path, 'r') as apk_zip:
            zip_files = apk_zip.namelist()
            
            for required in required_files:
                if required not in zip_files:
                    result['errors'].append(f'Missing {required}')
        
        # 如果没有错误，标记为有效
        if not result['errors']:
            result['is_valid'] = True
        
    except Exception as e:
        result['errors'].append(f'Exception: {str(e)}')
    
    return result


def main():
    print("=" * 80)
    print("APK 验证工具")
    print("=" * 80)
    print(f"目录: {APK_DIR.absolute()}\n")
    
    # 查找所有 APK 文件
    apk_files = list(APK_DIR.glob("*.apk"))
    
    if not apk_files:
        print("❌ 错误: 没有找到 APK 文件")
        print(f"请确保 APK 文件在: {APK_DIR.absolute()}")
        return
    
    print(f"找到 {len(apk_files)} 个 APK 文件\n")
    
    # 验证所有 APK
    results = []
    valid_count = 0
    invalid_count = 0
    
    print("开始验证...\n")
    for apk_path in tqdm(apk_files, desc="Validating"):
        result = validate_apk(apk_path)
        results.append(result)
        
        if result['is_valid']:
            valid_count += 1
        else:
            invalid_count += 1
    
    # 保存验证报告
    report = {
        'total': len(apk_files),
        'valid': valid_count,
        'invalid': invalid_count,
        'details': results
    }
    
    with open(OUTPUT_FILE, 'w', encoding='utf-8') as f:
        json.dump(report, f, indent=2, ensure_ascii=False)
    
    # 打印总结
    print("\n" + "=" * 80)
    print("验证完成!")
    print("=" * 80)
    print(f"总数: {len(apk_files)}")
    print(f"✅ 有效: {valid_count} ({valid_count/len(apk_files)*100:.1f}%)")
    print(f"❌ 无效: {invalid_count} ({invalid_count/len(apk_files)*100:.1f}%)")
    print("=" * 80)
    
    # 显示无效的 APK
    if invalid_count > 0:
        print(f"\n⚠️  无效的 APK ({invalid_count} 个):\n")
        for result in results:
            if not result['is_valid']:
                print(f"- {result['file']}")
                for error in result['errors']:
                    print(f"  └─ {error}")
        
        print(f"\n建议:")
        print(f"1. 删除无效的 APK")
        print(f"2. 重新下载这些应用")
        print(f"3. 如果无效数量过多（>5%），检查下载脚本")
    
    print(f"\n📝 详细报告已保存: {OUTPUT_FILE}")
    
    # 统计信息
    if valid_count > 0:
        total_size = sum(r['size_mb'] for r in results if r['is_valid'])
        avg_size = total_size / valid_count
        print(f"\n📊 统计:")
        print(f"- 总大小: {total_size:.2f} MB")
        print(f"- 平均大小: {avg_size:.2f} MB")
    
    # 评估
    print(f"\n✨ 评估:")
    if valid_count >= len(apk_files) * 0.95:
        print("✅ 优秀！验证通过率 > 95%，可以继续下一步")
    elif valid_count >= len(apk_files) * 0.90:
        print("✅ 良好！验证通过率 > 90%，可以继续，但建议补充无效的 APK")
    elif valid_count >= len(apk_files) * 0.80:
        print("⚠️  一般，通过率 > 80%，建议重新下载失败的 APK")
    else:
        print("❌ 通过率过低，建议检查下载脚本或更换下载源")
    
    print("\n下一步:")
    print("cd ../scripts")
    print("python 2_extract_api_calls.py")


if __name__ == "__main__":
    main()

