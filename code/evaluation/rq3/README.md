# Privacy Leakage 实验脚本

本目录包含所有实验脚本。**完整文档请查看 [../README.md](../README.md)**

---

## 脚本列表

| # | 脚本 | 功能 | 时间 |
|---|------|------|------|
| 0 | `download_androzoo_complete.py` | 下载 APK 数据集 | 3-5 小时 |
| 0 | `validate_apks.py` | 验证 APK 有效性 | 5 分钟 |
| 1 | `1_convert_mappings.py` | 转换映射文件 | 2 小时 |
| 2 | `2_extract_api_calls.py` | 提取 API 调用 | 2-5 小时 |
| 3 | `3_match_mappings.py` | 匹配映射 | 1 小时 |
| 4 | `4_calculate_coverage.py` | 计算覆盖率 | 30 分钟 |
| 5 | `5_detailed_statistics.py` | 详细统计 | 2 小时 |
| 6 | `6_generate_latex_table.py` | 生成 LaTeX 表格 | 30 分钟 |

---

## 快速开始

### 方法 A: 一键运行（推荐）

```bash
# 1. 下载 APK 数据集
export ANDROZOO_API_KEY="your_key_here"
python download_androzoo_complete.py

# 2. 验证
python validate_apks.py

# 3. 一键运行所有分析
python run_all.py
```

### 方法 B: 分步运行

```bash
# 下载和验证（同上）

# 手动运行每个步骤
python 1_convert_mappings.py
python 2_extract_api_calls.py --parallel 8
python 3_match_mappings.py
python 4_calculate_coverage.py
python 5_detailed_statistics.py
python 6_generate_latex_table.py
```

---

## 脚本开发状态

| 脚本 | 状态 |
|------|------|
| download_androzoo_complete.py | ✅ 完成 |
| validate_apks.py | ✅ 完成 |
| 1_convert_mappings.py | ✅ 完成 |
| 2_extract_api_calls.py | ✅ 完成 |
| 3_match_mappings.py | ✅ 完成 |
| 4_calculate_coverage.py | ✅ 完成 |
| 5_detailed_statistics.py | ✅ 完成 |
| 6_generate_latex_table.py | ✅ 完成 |
| run_all.py | ✅ 完成（一键运行） |

---

## 常用命令

### 下载相关

```bash
# 小规模测试（10+10）
# 修改脚本: BENIGN_COUNT=10, MALWARE_COUNT=10
python download_androzoo_complete.py

# 检查下载状态
ls ../apks/benign/*.apk | wc -l
ls ../apks/malware/*.apk | wc -l

# 验证 APK
python validate_apks.py
```

### 分析相关

```bash
# 并行分析（8 进程）
python 2_extract_api_calls.py --parallel 8

# 恢复中断的分析
python 2_extract_api_calls.py --resume

# 调试模式
python 2_extract_api_calls.py --verbose
```

### 结果查看

```bash
# 查看覆盖率
cat ../results/coverage_stats.csv

# 查看图表
ls ../results/*.png

# 查看 LaTeX 表格
cat ../results/privacy_coverage_table.tex
```

---

## 配置

### download_androzoo_complete.py
```python
BENIGN_COUNT = 500          # 良性应用数量
MALWARE_COUNT = 500         # 恶意应用数量
MAX_FILE_SIZE_MB = 50       # 最大文件大小
MIN_DATE = "2022-01-01"     # 最早日期
MAX_WORKERS = 4             # 并行下载数
```

### 2_extract_api_calls.py（待开发）
```python
MAX_WORKERS = 8             # 并行分析数
TIMEOUT_SECONDS = 300       # 超时时间
```

---

## 故障排除

### 下载问题

```bash
# API Key 无效
echo $ANDROZOO_API_KEY  # 检查是否设置

# 下载速度慢
# 修改脚本: MAX_WORKERS = 2

# SHA256 校验失败
cat ../apks/failed_downloads.txt  # 查看失败列表
```

### 分析问题

```bash
# 分析卡住
find ../apks -size +50M -delete  # 删除过大文件

# 内存不足
python 2_extract_api_calls.py --parallel 2  # 减少并行数
```

---

## 获取帮助

- **完整文档**: [../README.md](../README.md)
- **任务清单**: [../TODO.md](../TODO.md)
- **AndroZoo**: https://androzoo.uni.lu/

---

**下一步**: 
1. 查看 [../README.md](../README.md) 了解完整流程
2. 申请 AndroZoo API key
3. 运行 `download_androzoo_complete.py`
