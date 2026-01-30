# 配置说明

## API Key 配置

API key 已保存在 `config.py` 文件中，无需设置环境变量。

### 当前配置

```python
# config.py
ANDROZOO_API_KEY = "677dfcd4218257677f9d759fa5edefc4a697ca5479faf2ea9dce28af1963a7a4"

# 测试模式（快速验证）
BENIGN_COUNT = 5
MALWARE_COUNT = 5

# 正式模式（完整实验）
# BENIGN_COUNT = 500
# MALWARE_COUNT = 500
```

---

## 切换测试/正式模式

### 测试模式（5+5 APKs，约 15 分钟）

编辑 `config.py`:
```python
BENIGN_COUNT = 5
MALWARE_COUNT = 5
```

运行：
```bash
python download_androzoo_complete.py
python run_all.py
```

### 正式模式（500+500 APKs，约 3-8 小时）

编辑 `config.py`:
```python
BENIGN_COUNT = 500
MALWARE_COUNT = 500
```

运行：
```bash
python download_androzoo_complete.py
python run_all.py
```

---

## 其他配置项

```python
MAX_FILE_SIZE_MB = 50       # APK 最大文件大小
MIN_DATE = "2022-01-01"     # APK 最早发布日期
MALWARE_THRESHOLD = 10      # 恶意判定阈值
MAX_WORKERS = 4             # 下载并行数
ANALYSIS_WORKERS = 8        # 分析并行数
```

---

## 当前状态

- ✅ API Key: 已保存在 config.py
- ✅ 模式: 测试模式（5+5）
- ⏳ CSV 下载: ~32% (876 MB / 2,700 MB)

---

## CSV 下载完成后立即运行

```bash
cd scripts

# 验证 CSV（应该 2.5-2.9 GB）
dir latest.csv.gz

# 运行测试下载（5+5）
python download_androzoo_complete.py

# 如果测试成功，改为正式模式
# 编辑 config.py: BENIGN_COUNT = 500, MALWARE_COUNT = 500

# 运行正式下载
python download_androzoo_complete.py
```

