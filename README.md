# Bamboo: LLM-Driven API-Permission Mapping Discovery

## Project Overview

Bamboo is a research tool for discovering API-permission mappings in the Android Framework using large language models. This tool addresses the critical documentation gaps in the Android ecosystem by combining semantic code analysis with automated test validation.

### Key Features

- **API Extraction**: Extracts Java APIs from Android SDK source code using AST parsing and keyword matching
- **LLM-based Permission Analysis**: Employs a dual-role prompting strategy (Permission Detector and Permission Analyst) to predict required permissions
- **Automated Verification**: Generates, validates, and executes self-contained test cases to verify permission enforcement
- **Multi-Version Support**: Supports analysis across Android versions (Android 15 mappings provided)

## Repository Structure

```
LLMPerm-master/
├── README.md                    # This file
├── code/                        # All Source Code
│   ├── sdk_parser/             # SDK parsing module
│   │   ├── code/               # Core implementation
│   │   ├── llm_reply/          # LLM response results 
│   │   ├── llm_reply_java/     # Generated test code 
│   │   ├── baselines/          # Baseline comparison results
│   │   ├── statistics/        # Statistical results
│   │   └── permission_api.txt  # Final API-permission mappings
│   ├── document_parser/         # Document parsing module
│   ├── evaluation/             # Evaluation Scripts
│   │   └── rq3/               # RQ3 evaluation scripts
│   └── scripts/                # Reproduction Scripts
│       ├── rq1_reproduce.py
│       ├── rq2_reproduce.py
│       └── rq3_reproduce.py
└── data/                        # All Data
    ├── android_15/              # Android 15 (API 34) API-Permission Mappings
    ├── baselines/              # Baseline Mappings
    │   ├── dynamo/            # Dynamo mappings
    │   ├── arcade/            # Arcade mappings
    │   ├── natidroid/         # Natidroid mappings
    │   └── sdk_annotations/   # SDK Annotations
    ├── rq3/                    # RQ3 Experimental Data
    │   ├── app_analysis/      # Per-app analysis results
    │   │   ├── api_calls/     # API call analysis (1,183 apps)
    │   │   └── matched_mappings/ # Matched mappings (1,183 apps)
    │   └── statistics/        # Aggregated statistical results
    └── androzoo_sha256_list.txt # SHA256 hashes for 1,183 apps
```

## Pipeline Overview

Bamboo implements a three-phase pipeline for API permission mapping discovery:

1. **Android SDK API Extraction**: Extracts APIs and their contextual information from Android SDK source code
2. **LLM-based API Permission Analysis**: Analyzes each API using a dual-role prompting strategy to predict required permissions
3. **API Permission Verification**: Generates, validates, and executes test cases to verify permission enforcement

## Quick Start

### Requirements

- Python 3.7+
- Android SDK source code
- LLM API keys (OpenAI, Anthropic, or DeepSeek)

### Install Dependencies

```bash
pip install openai anthropic javalang beautifulsoup4 requests androguard
```

### Basic Usage

1. **Configure API Key**
   ```python
   # Set LLM API key in sdk_parser/code/llm_parser.py
   ```

2. **Run Analysis for Android SDK**
   ```bash
   cd code/sdk_parser/code
   python complete_pipeline.py --sdk-path <path-to-android-sdk> --api-level 34
   ```

3. **Reproduce Experiments**
   ```bash
   # RQ1: Effectiveness evaluation
   python code/scripts/rq1_reproduce.py
   
   # RQ2: Ablation study
   python code/scripts/rq2_reproduce.py
   
   # RQ3: Downstream impact
   python code/scripts/rq3_reproduce.py
   ```

Raw APK files from AndroZoo are not redistributed but can be obtained directly from AndroZoo using the SHA256 hashes provided in `data/androzoo_sha256_list.txt`.

## Citation

If you use Bamboo in your research, please cite:

```bibtex
@inproceedings{bamboo2025,
  title={Bamboo: LLM-Driven API-Permission Mapping Discovery in the Android Framework},
  author={...},
  booktitle={...},
  year={2025}
}
```
