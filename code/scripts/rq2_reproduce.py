#!/usr/bin/env python3
"""
RQ2 Reproduction Script: Ablation Study and LLM Comparison

This script reproduces the RQ2 experiments including:
- Ablation study with 2,000 APIs ground truth dataset
- LLM comparison across different models
"""

import sys
import os

# Add parent directory to path
sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

def main():
    print("RQ2: Ablation Study and LLM Comparison")
    print("=" * 50)
    print("\nThis script reproduces the RQ2 experiments.")
    print("Please refer to the documentation for detailed instructions.")
    print("\nRequired steps:")
    print("1. Load ablation study dataset (2,000 APIs with ground truth)")
    print("2. Run different pipeline variants (Detector-Only, Analyst-Only, No-Test, Full Pipeline)")
    print("3. Compare different LLM models")
    print("4. Calculate precision, recall, and F1-score")
    
    # Add your reproduction code here
    # Example: from code.sdk_parser.code.rq2_ablation_experiment import RQ2AblationExperiment

if __name__ == "__main__":
    main()
