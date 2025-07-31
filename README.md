# LLMPerm: LLM-Driven Discovery of API-Permission Mappings in the Android Framework

## Project Overview

LLMPerm is a research tool for discovering API-permission mappings in the Android Framework using large language models. This project explores automated approaches to address permission documentation challenges in Android development.

### Key Features

- **API Extraction**: Extracts Java APIs from Android SDK source code
- **Permission Analysis**: Uses LLM-based approaches for permission requirement analysis
- **Verification**: Implements automated verification mechanisms
- **Multi-Version Support**: Supports analysis across different Android SDK versions

## Project Architecture

```
LLMPerm-master/
├── document_parser/          # Document parsing module
├── sdk_parser/              # SDK parsing module
│   ├── code/                # Core implementation
│   ├── llm_reply/           # LLM response results
│   ├── llm_reply_java/      # Generated test code
│   ├── baselines/           # Baseline comparison results
│   └── statistics/          # Statistical results
└── permission_api.txt       # Final results
```

## Pipeline Overview

The project implements a multi-stage pipeline for API permission mapping discovery:

1. **API Extraction**: Extracts APIs from Android SDK
2. **Permission Analysis**: Analyzes permission requirements
3. **Verification**: Verifies predictions through testing

## Usage Instructions

### Requirements

- Python 3.7+
- Android SDK
- OpenAI API key

### Install Dependencies

```bash
pip install openai javalang beautifulsoup4 requests
```

### Basic Usage

1. **Configure API Key**
   ```python
   # Set OpenAI API key in llm_parser.py
   key = 'your-openai-api-key'
   ```

2. **Run Analysis**
   ```bash
   cd sdk_parser/code
   python llm_parser.py
   ```

3. **Generate Test Cases**
   ```bash
   python llm_parser.py --generate-tests
   ```

## Output

The tool generates permission mapping results and test cases for APIs requiring permissions.

## Security Notice

⚠️ **Important**: This project requires API keys for LLM services. Please ensure proper security practices when handling sensitive credentials.


*Note: This project requires OpenAI API access to run LLM analysis functions properly.* 
