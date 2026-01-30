# GitHub推送指南

## 问题
仓库大小约3.14 GiB，直接推送可能超时。

## 解决方案

### 方案1: 使用GitHub Desktop（推荐）
1. 下载安装 [GitHub Desktop](https://desktop.github.com/)
2. 打开GitHub Desktop，选择 "File" -> "Add Local Repository"
3. 选择本地的 `LLMPerm-master` 目录
4. 点击 "Publish repository" 推送到GitHub
5. GitHub Desktop会自动处理大文件推送

### 方案2: 使用Git LFS（Large File Storage）
如果仓库中有大文件需要版本控制：

```bash
# 安装Git LFS
git lfs install

# 追踪大文件类型
git lfs track "*.json"
git lfs track "*.csv"

# 重新添加文件
git add .gitattributes
git add .
git commit -m "Add Git LFS tracking"
git push origin main
```

### 方案3: 分批推送
```bash
# 1. 先推送代码部分
git add code/
git commit -m "Add code directory"
git push origin main

# 2. 再推送数据部分（如果还是太大，可以进一步拆分）
git add data/android_15/
git commit -m "Add Android 15 mappings"
git push origin main

git add data/baselines/
git commit -m "Add baseline mappings"
git push origin main

# 3. 最后推送RQ3数据
git add data/rq3/
git commit -m "Add RQ3 experimental data"
git push origin main
```

### 方案4: 使用SSH方式（如果HTTPS有问题）
```bash
# 更改远程URL为SSH
git remote set-url origin git@github.com:huhanGitHub/LLMPerm.git

# 推送
git push -u origin main
```

### 方案5: 压缩历史记录（如果历史记录太大）
```bash
# 创建一个新的orphan分支（无历史）
git checkout --orphan new-main
git add .
git commit -m "Initial commit: Reorganized repository structure"
git branch -D main
git branch -m main
git push -f origin main
```

### 方案6: 使用GitHub CLI
```bash
# 安装GitHub CLI后
gh repo create huhanGitHub/LLMPerm --public --source=. --remote=origin --push
```

## 推荐流程
1. 首先尝试**方案1（GitHub Desktop）**，最简单可靠
2. 如果还是失败，尝试**方案3（分批推送）**
3. 如果网络不稳定，尝试**方案4（SSH方式）**
