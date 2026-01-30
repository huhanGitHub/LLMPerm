# Android 15 (API 34) API-Permission Mappings

## Status

This directory contains complete API-permission mappings for Android 15 (API 34).

## Files

- `bamboo_mapping.json`: Complete API-permission mappings in JSON format
- `permission_mappings.txt`: Human-readable format

## Statistics (from Table 1)

- **Covered APIs**: 15,138
- **Permission-Required APIs**: 3,264

## Usage

These mappings are used in:
- RQ1: Effectiveness evaluation (precision validation)
- RQ3: Downstream impact analysis (privacy analysis, security risk assessment, permission-API consistency detection)

## Format

### JSON Format (`bamboo_mapping.json`)

```json
{
  "java_file_path.method_name": {
    "permissions": ["PERMISSION_NAME"],
    "api_level": 34,
    ...
  }
}
```

### Text Format (`permission_mappings.txt`)

Human-readable format with one mapping per line:
```
java_file_path.method_name -> PERMISSION_NAME
```
