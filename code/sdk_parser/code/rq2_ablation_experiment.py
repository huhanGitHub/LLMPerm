"""
RQ2 Ablation Study Experiment Script
Randomly sample 1000 APIs from Android 15 SDK and test different pipeline variants
"""

import json
import os
import random
import sys
from typing import Dict, List, Tuple
from pathlib import Path

# Add current directory to path
sys.path.append(os.path.dirname(os.path.abspath(__file__)))

from java_parser import extract_java_method_info_from_file, find_java_files
from llm_parser import (
    method_permission_check, 
    advanced_dual_role_permission_check,
    contact_chatGPT
)
from code_validation_agent import validation_agent


class RQ2AblationExperiment:
    """RQ2 Ablation Study Experiment"""
    
    def __init__(self, sdk_path: str, output_dir: str = "rq2_experiment_output"):
        self.sdk_path = sdk_path
        self.output_dir = output_dir
        os.makedirs(output_dir, exist_ok=True)
        
        # Pipeline variants as described in RQ2
        self.variants = {
            'detector_only': self._detector_only,
            'analyst_only': self._analyst_only,
            'dual_role': self._dual_role,
            'no_test': self._no_test,
            'full_pipeline': self._full_pipeline
        }
        
        # Results storage
        self.results = {}
        self.ground_truth = {}  # Will be populated from RQ1 results
        
    def extract_all_apis(self, max_apis: int = None) -> List[Dict]:
        """
        Extract all APIs from Android SDK
        Returns list of API dictionaries with file path and method info
        """
        print("Extracting all APIs from Android SDK...")
        
        # Try to load from existing JSON file first
        json_file = os.path.join(self.output_dir, "all_apis_android15.json")
        if os.path.exists(json_file):
            print(f"Loading existing API list from {json_file}")
            with open(json_file, 'r', encoding='utf-8') as f:
                return json.load(f)
        
        # Find all Java files
        java_files = find_java_files(self.sdk_path)
        print(f"Found {len(java_files)} Java files")
        
        if max_apis:
            java_files = java_files[:max_apis]
            print(f"Limited to {max_apis} files for testing")
        
        # Extract all methods
        all_apis = []
        extracted_count = 0
        
        for i, java_file in enumerate(java_files):
            if i % 100 == 0:
                print(f"Processing file {i+1}/{len(java_files)}: {os.path.basename(java_file)}")
            
            try:
                methods_info = extract_java_method_info_from_file(java_file)
                if methods_info:
                    for method_info in methods_info:
                        all_apis.append({
                            'java_file_path': java_file,
                            'method_name': method_info['method_name'],
                            'method_info': method_info
                        })
                        extracted_count += 1
            except Exception as e:
                print(f"Error processing {java_file}: {e}")
                continue
        
        # Save extracted APIs
        print(f"Extracted {extracted_count} APIs from {len(java_files)} files")
        with open(json_file, 'w', encoding='utf-8') as f:
            json.dump(all_apis, f, indent=2, ensure_ascii=False)
        
        return all_apis
    
    def sample_1000_apis(self, all_apis: List[Dict], seed: int = 42) -> List[Dict]:
        """
        Randomly sample 1000 APIs from all APIs
        Includes both positive and negative cases
        """
        print(f"Randomly sampling 1000 APIs from {len(all_apis)} total APIs...")
        random.seed(seed)
        sampled = random.sample(all_apis, min(1000, len(all_apis)))
        
        # Save sampled APIs
        sample_file = os.path.join(self.output_dir, "sampled_1000_apis.json")
        with open(sample_file, 'w', encoding='utf-8') as f:
            json.dump(sampled, f, indent=2, ensure_ascii=False)
        
        print(f"Sampled {len(sampled)} APIs, saved to {sample_file}")
        return sampled
    
    def load_ground_truth_from_rq1(self, rq1_results_path: str = None):
        """
        Load ground truth from RQ1 validation results
        For each sampled API, check if it was validated as requiring permission in RQ1
        """
        print("Loading ground truth from RQ1 results...")
        
        # If RQ1 results file is provided, load it
        if rq1_results_path and os.path.exists(rq1_results_path):
            with open(rq1_results_path, 'r', encoding='utf-8') as f:
                rq1_results = json.load(f)
                # Build ground truth dictionary
                for result in rq1_results:
                    api_key = f"{result['java_file_path']}.{result['method_name']}"
                    # Mark as positive if verified by Full Pipeline in RQ1
                    self.ground_truth[api_key] = result.get('verified', False)
        else:
            print("Warning: RQ1 results not found. Ground truth will be empty.")
            print("For this test run, we'll use the Full Pipeline results as ground truth.")
    
    def _detector_only(self, method_code: str) -> Dict:
        """Detector-Only variant: Only Permission Detector checks"""
        model_id = "gpt-4o-mini"
        
        demonstration_cases = """
Examples of Permission-Required and Permission-Free APIs:

Example 1 (Permission-Free):
public int addNumbers(int a, int b) {
  return a + b;
}
Result: No

Example 2 (Explicit Permission Check):
public boolean hasLocationPermission(Context context) {
  int permissionCheck = ContextCompat.checkSelfPermission(context,
    Manifest.permission.ACCESS_FINE_LOCATION);
  return permissionCheck == PackageManager.PERMISSION_GRANTED;
}
Result: Yes, requires ACCESS_FINE_LOCATION permission
"""
        
        prompt = (
            "Acts as a Permission Detector, identifying permission usages in the Java method "
            "by checking for invoked API calls that involve permissions.\n\n"
            f"{demonstration_cases}\n"
            "Now analyze the following Java method code and determine if it requires any permissions:\n"
            f"{method_code}\n\n"
            "Reply format: 'Yes' + very short reasons if permissions are required, or 'No' if no permissions are needed."
        )
        
        reply = contact_chatGPT(model_id, prompt)
        return {
            'response': reply,
            'requires_permission': reply and 'Yes' in reply
        }
    
    def _analyst_only(self, method_code: str) -> Dict:
        """Analyst-Only variant: Only Permission Analyst infers"""
        model_id = "gpt-4o-mini"
        
        demonstration_cases = """
Examples of Permission-Required and Permission-Free APIs:

Example 1 (Permission-Free):
public int addNumbers(int a, int b) {
  return a + b;
}
Result: No

Example 3 (GPS Status Check):
public boolean isGPSEnabled(Context context) {
  LocationManager locationManager =
    (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
  return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
}
Result: Yes, requires location access permissions
"""
        
        prompt = (
            "Act as a Permission Analyst, analyzing Java method functions to infer necessary permissions "
            "based on their operational characteristics.\n"
            "Android permissions are more likely involved in functions like Hardware Access, Network Access, "
            "Storage Access, Location Access, Media Access, and System Tools.\n\n"
            f"{demonstration_cases}\n"
            "Now analyze the following Java method code and determine if it requires any permissions:\n"
            f"{method_code}\n\n"
            "Reply format: 'Yes' + very short reasons if permissions are required, or 'No' if no permissions are needed."
        )
        
        reply = contact_chatGPT(model_id, prompt)
        return {
            'response': reply,
            'requires_permission': reply and 'Yes' in reply
        }
    
    def _dual_role(self, method_code: str) -> Dict:
        """Dual-Role variant: Both detector and analyst without test generation"""
        result = advanced_dual_role_permission_check(method_code)
        return {
            'response': result['final_decision'],
            'requires_permission': result['final_decision'] and 'Yes' in result['final_decision']
        }
    
    def _no_test(self, method_code: str) -> Dict:
        """No-Test variant: Complete dual-role pipeline but skip dynamic test validation"""
        # Same as dual-role, but we'll mark it differently for tracking
        result = advanced_dual_role_permission_check(method_code)
        return {
            'response': result['final_decision'],
            'requires_permission': result['final_decision'] and 'Yes' in result['final_decision'],
            'test_skipped': True
        }
    
    def _full_pipeline(self, api_data: Dict) -> Dict:
        """Full Pipeline: Complete tool with all components"""
        method_code = str(api_data['method_info'])
        
        # Phase 1: Dual-role permission check
        result = advanced_dual_role_permission_check(method_code)
        
        # Phase 2: Test generation (simplified for experiment)
        # In real pipeline, this would generate and execute test cases
        test_generated = False
        if result['final_decision'] and 'Yes' in result['final_decision']:
            # For experiment, we'll just mark that test would be generated
            test_generated = True
        
        return {
            'response': result['final_decision'],
            'requires_permission': result['final_decision'] and 'Yes' in result['final_decision'],
            'test_generated': test_generated,
            'confidence': result.get('confidence', 'medium')
        }
    
    def run_variant(self, variant_name: str, sampled_apis: List[Dict]) -> Dict:
        """
        Run a specific pipeline variant on sampled APIs
        """
        print(f"\n{'='*60}")
        print(f"Running {variant_name.upper()} variant")
        print(f"{'='*60}")
        
        variant_func = self.variants[variant_name]
        results = []
        discovered_count = 0
        
        for i, api_data in enumerate(sampled_apis):
            if i % 50 == 0:
                print(f"Processing API {i+1}/{len(sampled_apis)}: {api_data['method_name']}")
            
            try:
                method_code = str(api_data['method_info'])
                
                if variant_name == 'full_pipeline':
                    result = variant_func(api_data)
                else:
                    result = variant_func(method_code)
                
                api_result = {
                    'java_file_path': api_data['java_file_path'],
                    'method_name': api_data['method_name'],
                    'variant': variant_name,
                    'prediction': result.get('requires_permission', False),
                    'response': result.get('response', ''),
                    **result
                }
                
                if result.get('requires_permission', False):
                    discovered_count += 1
                
                results.append(api_result)
                
            except Exception as e:
                print(f"Error processing {api_data['method_name']}: {e}")
                results.append({
                    'java_file_path': api_data['java_file_path'],
                    'method_name': api_data['method_name'],
                    'variant': variant_name,
                    'prediction': False,
                    'error': str(e)
                })
        
        # Save results
        output_file = os.path.join(self.output_dir, f"{variant_name}_results.json")
        with open(output_file, 'w', encoding='utf-8') as f:
            json.dump(results, f, indent=2, ensure_ascii=False)
        
        print(f"\n{variant_name} completed:")
        print(f"  - APIs discovered: {discovered_count}/{len(sampled_apis)}")
        print(f"  - Results saved to: {output_file}")
        
        return {
            'variant': variant_name,
            'total_apis': len(sampled_apis),
            'apis_discovered': discovered_count,
            'results': results,
            'output_file': output_file
        }
    
    def calculate_metrics(self, variant_results: Dict, ground_truth: Dict) -> Dict:
        """
        Calculate precision, recall, and F1-score for a variant
        """
        results = variant_results['results']
        
        # Count TP, FP, TN, FN
        tp = 0  # True Positive: predicted Yes, ground truth Yes
        fp = 0  # False Positive: predicted Yes, ground truth No
        tn = 0  # True Negative: predicted No, ground truth No
        fn = 0  # False Negative: predicted No, ground truth Yes
        
        for result in results:
            api_key = f"{result['java_file_path']}.{result['method_name']}"
            predicted = result.get('prediction', False)
            actual = ground_truth.get(api_key, None)
            
            # If ground truth is not available, skip this API
            if actual is None:
                continue
            
            if predicted and actual:
                tp += 1
            elif predicted and not actual:
                fp += 1
            elif not predicted and not actual:
                tn += 1
            elif not predicted and actual:
                fn += 1
        
        # Calculate metrics
        precision = tp / (tp + fp) if (tp + fp) > 0 else 0.0
        recall = tp / (tp + fn) if (tp + fn) > 0 else 0.0
        f1_score = 2 * (precision * recall) / (precision + recall) if (precision + recall) > 0 else 0.0
        
        return {
            'tp': tp,
            'fp': fp,
            'tn': tn,
            'fn': fn,
            'precision': precision,
            'recall': recall,
            'f1_score': f1_score,
            'apis_with_ground_truth': tp + fp + tn + fn
        }
    
    def run_experiment(self, sample_size: int = 1000, seed: int = 42, 
                      rq1_results_path: str = None, test_mode: bool = True):
        """
        Run the complete RQ2 ablation study experiment
        
        Args:
            sample_size: Number of APIs to sample (default 1000)
            seed: Random seed for reproducibility
            rq1_results_path: Path to RQ1 validation results for ground truth
            test_mode: If True, use smaller sample and skip some variants for testing
        """
        print("="*60)
        print("RQ2 Ablation Study Experiment")
        print("="*60)
        
        # Step 1: Extract all APIs
        all_apis = self.extract_all_apis(max_apis=100 if test_mode else None)
        
        # Step 2: Sample APIs
        sampled_apis = self.sample_1000_apis(all_apis, seed=seed)
        if test_mode:
            sampled_apis = sampled_apis[:10]  # Use only 10 for testing
            print(f"TEST MODE: Using only {len(sampled_apis)} APIs")
        
        # Step 3: Load ground truth (if available)
        self.load_ground_truth_from_rq1(rq1_results_path)
        
        # If no ground truth, use Full Pipeline as reference
        if not self.ground_truth:
            print("No RQ1 ground truth found. Running Full Pipeline first to establish ground truth...")
            full_pipeline_results = self.run_variant('full_pipeline', sampled_apis)
            # Build ground truth from Full Pipeline results
            for result in full_pipeline_results['results']:
                api_key = f"{result['java_file_path']}.{result['method_name']}"
                self.ground_truth[api_key] = result.get('prediction', False)
        
        # Step 4: Run all variants
        variant_results = {}
        variants_to_run = ['detector_only', 'analyst_only', 'dual_role', 'no_test']
        if not test_mode:
            variants_to_run.append('full_pipeline')
        
        for variant_name in variants_to_run:
            variant_result = self.run_variant(variant_name, sampled_apis)
            variant_results[variant_name] = variant_result
        
        # Step 5: Calculate metrics
        print("\n" + "="*60)
        print("Calculating Metrics")
        print("="*60)
        
        metrics_summary = {}
        for variant_name, variant_result in variant_results.items():
            metrics = self.calculate_metrics(variant_result, self.ground_truth)
            metrics_summary[variant_name] = {
                'apis_discovered': variant_result['apis_discovered'],
                **metrics
            }
            
            print(f"\n{variant_name.upper()}:")
            print(f"  APIs Discovered: {metrics_summary[variant_name]['apis_discovered']}")
            print(f"  Precision: {metrics['precision']:.1%}")
            print(f"  Recall: {metrics['recall']:.1%}")
            print(f"  F1-Score: {metrics['f1_score']:.1%}")
            print(f"  Ground Truth Coverage: {metrics['apis_with_ground_truth']}/{len(sampled_apis)}")
        
        # Step 6: Save summary
        summary_file = os.path.join(self.output_dir, "experiment_summary.json")
        with open(summary_file, 'w', encoding='utf-8') as f:
            json.dump({
                'sample_size': len(sampled_apis),
                'seed': seed,
                'ground_truth_size': len(self.ground_truth),
                'variant_results': variant_results,
                'metrics_summary': metrics_summary
            }, f, indent=2, ensure_ascii=False)
        
        print(f"\nExperiment summary saved to: {summary_file}")
        return metrics_summary


def main():
    """Main function to run the experiment"""
    # Configuration
    # Note: Update these paths according to your setup
    sdk_path = r"android-sdk-sources-for-api-level-35-master"  # Android 15 = API 35
    output_dir = "rq2_experiment_output"
    
    # Check if SDK path exists
    if not os.path.exists(sdk_path):
        print(f"Error: SDK path not found: {sdk_path}")
        print("Please update the sdk_path variable in the script.")
        return
    
    # Create experiment instance
    experiment = RQ2AblationExperiment(sdk_path, output_dir)
    
    # Run experiment in test mode (small sample, quick test)
    print("Running experiment in TEST MODE (10 APIs, quick test)...")
    print("Set test_mode=False for full experiment with 1000 APIs")
    
    metrics = experiment.run_experiment(
        sample_size=1000,
        seed=42,
        rq1_results_path=None,  # Update if you have RQ1 results
        test_mode=True  # Set to False for full experiment
    )
    
    print("\n" + "="*60)
    print("Experiment Completed!")
    print("="*60)


if __name__ == "__main__":
    main()

