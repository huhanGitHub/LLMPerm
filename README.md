# LLMPerm: LLM-Driven Discovery of API-Permission Mappings in the Android Framework

## Project Overview

LLMPerm is a large language model (LLM)-based tool for discovering API-permission mappings in the Android Framework. This project aims to address the issues of incomplete and inconsistent API permission documentation in Android development by using an innovative three-phase pipeline to automatically discover and verify API-permission mapping relationships in the Android SDK.

### Key Features

- **Comprehensive API Extraction**: Uses AST parsing and keyword matching techniques to extract all Java APIs from Android SDK source code
- **LLM-Driven Permission Analysis**: Employs dual-role prompting strategy, enabling LLM to act as both permission detector and permission analyst
- **Automated Verification**: Creates self-contained test cases through LLM code generation to verify predicted permission requirements
- **Multi-Version Support**: Supports permission mapping analysis across multiple Android SDK versions
- **High Coverage**: Discovers 32.2%-69.8% more API-permission mappings compared to existing baseline methods

## Project Architecture

```
LLMPerm-master/
├── document_parser/          # Document parsing module
│   ├── crawler.py           # Android official documentation crawler
│   ├── compare_results.py   # Result comparison tool
│   ├── permission_pairs.txt # Permission pairs extracted from documentation
│   └── NtDroid29.txt        # Baseline dataset
├── sdk_parser/              # SDK parsing module
│   ├── code/                # Core code implementation
│   │   ├── llm_parser.py    # Main LLM parser program
│   │   ├── java_parser.py   # Java code parser
│   │   ├── data_util.py     # Data processing utilities
│   │   └── statistics.py    # Statistical analysis tools
│   ├── llm_reply/           # LLM response results
│   ├── llm_reply_java/      # Generated Java test code
│   ├── baselines/           # Baseline comparison results
│   └── statistics/          # Statistical results
└── permission_api.txt       # Final permission mapping results
```

## Three-Phase Pipeline

### Phase 1: Android SDK API Extraction

**Objective**: Comprehensively extract all Java APIs from Android SDK source code

**Implementation Methods**:
- **AST Parsing**: Uses the `javalang` library to build abstract syntax trees of Java code, accurately identifying method signatures
- **Keyword Matching**: Complements AST parsing by identifying access modifiers, return types, annotations, etc.
- **Contextual Information Extraction**: Extracts metadata such as API level, deprecation status, documentation comments, etc.

**Output**: Structured API database containing method signatures and contextual information

### Phase 2: LLM-Driven API Permission Analysis

**Objective**: Use LLM to analyze extracted APIs and predict required Android permissions

**Core Strategy**:
- **Dual-Role Prompting**: 
  - Permission Detector: Analyzes semantic information to identify explicit permission requirements
  - Permission Analyst: Infers implicit permission requirements based on functional characteristics
- **Pre-demonstration Cases**: Enhances LLM's in-context learning capabilities through high-quality examples

**Analysis Scope**:
- Hardware Access
- Network Access 
- Storage Access
- Location Access
- Media Access
- System Tools

### Phase 3: API Permission Verification

**Objective**: Verify predicted permission requirements through automated testing

**Verification Methods**:
- **LLM Code Generation**: Generates self-contained test cases for APIs requiring permissions
- **Emulator Testing**: Executes test cases in Android emulator
- **Permission Verification**: Confirms permission checking behavior during API calls

## Usage Instructions

### Requirements

- Python 3.7+
- Android SDK
- OpenAI API key (for LLM calls)

### Install Dependencies

```bash
pip install openai javalang beautifulsoup4 requests
```s

### Running Process

1. **Configure API Key**
   ```python
   # Set OpenAI API key in llm_parser.py
   key = 'your-openai-api-key'
   ```

2. **Extract Android SDK APIs**
   ```bash
   cd sdk_parser/code
   python java_parser.py
   ```

3. **Execute LLM Permission Analysis**
   ```bash
   python llm_parser.py
   ```

4. **Generate Verification Code**
   ```bash
   python llm_parser.py --generate-tests
   ```

5. **Compare Results**
   ```bash
   cd ../document_parser
   python compare_results.py
   ```

## Output Format

### Permission Mapping Results (permission_api.txt)
```
file_path,method_name,permission_prediction_result,reason
android-sdk-sources-for-api-level-35-master/java/net/Socket.java,connect,Yes,Network connection requires INTERNET permission
```

### Generated Test Code (llm_reply_java/)
Contains Java test cases generated for each API requiring permissions, which can be run directly in Android projects.

## Contact Information

For questions or suggestions, please contact the project maintainers.

---

*Note: This project requires OpenAI API access to run LLM analysis functions properly.* 
