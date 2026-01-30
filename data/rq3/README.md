# RQ3: Downstream Impact Analysis

## Overview

This directory contains experimental data for RQ3, which evaluates the downstream impact of Bamboo's API-permission mappings through three scenarios: Privacy Analysis, Security Risk Assessment, and Permission-API Consistency Detection.

## Directory Structure

```
rq3/
├── app_analysis/         # Per-app analysis results
│   ├── api_calls/       # API call analysis for each app (1,183 apps)
│   └── matched_mappings/ # Matched mappings for each app
└── statistics/          # Aggregated statistical results
    ├── coverage_stats.csv
    ├── security_risk_stats.csv
    └── over_privileged_stats.csv
```

## Dataset

- **Total Apps**: 1,182
- **Benign Apps**: 573
- **Malware Apps**: 609
- **Source**: AndroZoo repository

### App Identification

SHA256 hashes for all 1,182 apps are provided in `data/androzoo_sha256_list.txt`. Raw APK files are not redistributed but can be obtained directly from AndroZoo using these SHA256 hashes.

## Experimental Scenarios

### 1. Privacy Analysis (Coverage)

Evaluates how many sensitive API calls are detected by each mapping tool.

**Results**: See `statistics/coverage_stats.csv`

- **Bamboo**: 16,715 total sensitive calls, 100% coverage, 7.13 avg unique APIs per app
- **Dynamo**: 5,191 total sensitive calls, 31.06% coverage, 1.96 avg unique APIs per app
- **SDK Annotations**: 2,120 total sensitive calls, 12.68% coverage, 0.94 avg unique APIs per app

### 2. Security Risk Assessment

Compares statistical features (mean, standard deviation) of sensitive API call counts between benign and malware apps.

**Results**: See `statistics/security_risk_stats.csv`

- **Bamboo**: Benign mean=44.42±36.29, Malware mean=13.45±20.55, Cohen's d=1.05 (large effect)
- **Dynamo**: Benign mean=14.63±19.50, Malware mean=6.37±14.02, Cohen's d=0.49 (small effect)
- **SDK Annotations**: Benign mean=6.29±8.40, Malware mean=2.00±4.11, Cohen's d=0.65 (medium effect)

### 3. Permission-API Consistency Detection

Identifies over-privileged apps (apps with unused permissions).

**Results**: See `statistics/over_privileged_stats.csv`

- **Bamboo**: 1,158 over-privileged apps (98.89%), 24,371 total unused permissions, 21.05 avg unused permissions per app
- **Dynamo**: 1,157 over-privileged apps (98.80%), 23,833 total unused permissions, 20.60 avg unused permissions per app
- **SDK Annotations**: 1,157 over-privileged apps (98.80%), 24,212 total unused permissions, 20.93 avg unused permissions per app

## Reproducing RQ3 Results

To reproduce RQ3 results:

1. Download APK files from AndroZoo using SHA256 hashes in `data/androzoo_sha256_list.txt`
2. Run analysis:
   ```bash
   python code/scripts/rq3_reproduce.py
   ```

## File Formats

### API Calls (`app_analysis/api_calls/*.json`)

Each file contains API calls detected in one app:
```json
{
  "package_name": "...",
  "api_calls": [
    {
      "class": "...",
      "method": "...",
      "permission": "..."
    }
  ]
}
```

### Matched Mappings (`app_analysis/matched_mappings/*.json`)

Each file contains matched mappings for one app, showing which APIs from the mapping are used.

### Statistics Files (CSV)

All statistics files use comma-separated values with headers:
- `mapping`: Tool name (bamboo, dynamo, sdk_annotations)
- Additional columns vary by file (see file headers)
