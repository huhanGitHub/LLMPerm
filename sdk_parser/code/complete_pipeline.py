"""
Complete LLMPerm Pipeline Implementation
Integrates all components to implement the three-phase pipeline described in the paper
"""

import json
import os
import time
from typing import Dict, List, Optional
from java_parser import extract_java_method_info_from_file, find_java_files, save_extraction_results
from llm_parser import method_permission_check, advanced_dual_role_permission_check, add_demonstration_case
from rag_interface import rag_manager
from code_validation_agent import validation_agent
from emulator_interface import emulator_interface


class LLMPermPipeline:
    """Complete LLMPerm pipeline implementation"""
    
    def __init__(self, sdk_path: str, output_dir: str = "output"):
        self.sdk_path = sdk_path
        self.output_dir = output_dir
        self.results = {
            'phase1_results': {},
            'phase2_results': {},
            'phase3_results': {}
        }
        
        # Create output directory
        os.makedirs(output_dir, exist_ok=True)
    
    def run_complete_pipeline(self, sdk_version: int = 35, max_apis: int = None) -> Dict:
        """
        Run the complete three-phase pipeline
        Args:
            sdk_version: Android SDK version to analyze
            max_apis: Maximum number of APIs to process (for testing)
        Returns:
            Complete pipeline results
        """
        print("Starting LLMPerm Complete Pipeline")
        print("=" * 50)
        
        # Phase 1: Android SDK API Extraction
        print("\nPhase 1: Android SDK API Extraction")
        print("-" * 30)
        phase1_results = self._run_phase1_api_extraction(sdk_version, max_apis)
        self.results['phase1_results'] = phase1_results
        
        # Phase 2: LLM-based API Permission Analysis
        print("\nPhase 2: LLM-based API Permission Analysis")
        print("-" * 40)
        phase2_results = self._run_phase2_permission_analysis(phase1_results)
        self.results['phase2_results'] = phase2_results
        
        # Phase 3: API Permission Verification
        print("\nPhase 3: API Permission Verification")
        print("-" * 35)
        phase3_results = self._run_phase3_verification(phase2_results, sdk_version)
        self.results['phase3_results'] = phase3_results
        
        # Generate final report
        self._generate_final_report()
        
        return self.results
    
    def _run_phase1_api_extraction(self, sdk_version: int, max_apis: int = None) -> Dict:
        """Phase 1: Extract APIs from Android SDK"""
        print("Extracting APIs from Android SDK...")
        
        # Find all Java files
        java_files = find_java_files(self.sdk_path)
        print(f"Found {len(java_files)} Java files")
        
        if max_apis:
            java_files = java_files[:max_apis]
            print(f"Limited to {max_apis} files for testing")
        
        # Extract method information
        all_methods_data = []
        extracted_count = 0
        
        for i, java_file in enumerate(java_files):
            if i % 100 == 0:
                print(f"Processing file {i+1}/{len(java_files)}: {os.path.basename(java_file)}")
            
            methods_info = extract_java_method_info_from_file(java_file)
            if methods_info:
                all_methods_data.append({
                    'java_file_path': java_file,
                    'methods_info': methods_info
                })
                extracted_count += len(methods_info)
        
        # Save extraction results
        output_file = os.path.join(self.output_dir, f"phase1_extracted_apis_sdk{sdk_version}.json")
        save_extraction_results(all_methods_data, output_file)
        
        print(f"Phase 1 completed: Extracted {extracted_count} methods from {len(all_methods_data)} files")
        
        return {
            'total_files_processed': len(java_files),
            'files_with_methods': len(all_methods_data),
            'total_methods_extracted': extracted_count,
            'output_file': output_file
        }
    
    def _run_phase2_permission_analysis(self, phase1_results: Dict) -> Dict:
        """Phase 2: LLM-based permission analysis"""
        print("Running LLM-based permission analysis...")
        
        # Load extracted APIs
        with open(phase1_results['output_file'], 'r') as f:
            all_methods_data = json.load(f)
        
        # Analyze permissions for each method
        permission_results = []
        analyzed_count = 0
        
        for file_data in all_methods_data:
            java_file_path = file_data['java_file_path']
            methods_info = file_data['methods_info']
            
            for method_info in methods_info:
                method_name = method_info['method_name']
                
                # Use dual-role permission checking
                result = advanced_dual_role_permission_check(str(method_info))
                
                permission_results.append({
                    'java_file_path': java_file_path,
                    'method_name': method_name,
                    'detector_response': result['detector_response'],
                    'analyst_response': result['analyst_response'],
                    'final_decision': result['final_decision'],
                    'confidence': result['confidence'],
                    'reasoning': result['reasoning']
                })
                
                analyzed_count += 1
                
                if analyzed_count % 50 == 0:
                    print(f"Analyzed {analyzed_count} methods...")
        
        # Save permission analysis results
        output_file = os.path.join(self.output_dir, "phase2_permission_analysis.json")
        with open(output_file, 'w', encoding='utf-8') as f:
            json.dump(permission_results, f, indent=2, ensure_ascii=False)
        
        # Filter APIs that require permissions
        permission_required_apis = [
            result for result in permission_results 
            if result['final_decision'] and 'Yes' in result['final_decision']
        ]
        
        print(f"Phase 2 completed: Analyzed {analyzed_count} methods")
        print(f"Found {len(permission_required_apis)} APIs requiring permissions")
        
        return {
            'total_methods_analyzed': analyzed_count,
            'permission_required_count': len(permission_required_apis),
            'permission_not_required_count': analyzed_count - len(permission_required_apis),
            'permission_required_apis': permission_required_apis,
            'output_file': output_file
        }
    
    def _run_phase3_verification(self, phase2_results: Dict, sdk_version: int) -> Dict:
        """Phase 3: API permission verification"""
        print("Running API permission verification...")
        
        permission_required_apis = phase2_results['permission_required_apis']
        verification_results = []
        verified_count = 0
        
        for api_result in permission_required_apis:
            target_api = f"{api_result['java_file_path']}.{api_result['method_name']}"
            
            print(f"Verifying API: {target_api}")
            
            # Step 1: Try to find existing test case using RAG
            existing_test_case = rag_manager.get_best_existing_test_case(
                api_result['method_name']
            )
            
            if existing_test_case:
                repo_name, test_case_content = existing_test_case
                print(f"Found existing test case in {repo_name}")
                
                # Normalize existing test case
                normalized_content = rag_manager.normalize_test_case(
                    test_case_content, api_result['method_name']
                )
            else:
                print("No existing test case found, generating new one...")
                
                # Generate new test case using LLM
                from llm_parser import API_code_generation
                normalized_content = API_code_generation(
                    api_result['java_file_path'], 
                    os.path.basename(api_result['java_file_path']).replace('.java', ''),
                    api_result['method_name']
                )
            
            # Step 2: Validate test case using Code Validation Agent
            validation_result = validation_agent.validate_test_case(
                normalized_content, target_api, sdk_version
            )
            
            if validation_result['is_valid']:
                final_test_case = validation_result['refined_content']
                print("Test case validation successful")
            else:
                print("Test case validation failed, using original")
                final_test_case = normalized_content
            
            # Step 3: Execute test case on emulator (if available)
            emulator_result = None
            if hasattr(emulator_interface, 'device_id') and emulator_interface.device_id:
                try:
                    emulator_result = emulator_interface.run_test_case(
                        final_test_case, target_api
                    )
                    print("Emulator execution completed")
                except Exception as e:
                    print(f"Emulator execution failed: {e}")
            
            # Compile verification result
            verification_result = {
                'target_api': target_api,
                'llm_prediction': api_result['final_decision'],
                'confidence': api_result['confidence'],
                'test_case_content': final_test_case,
                'validation_result': validation_result,
                'emulator_result': emulator_result,
                'verification_timestamp': time.time()
            }
            
            verification_results.append(verification_result)
            verified_count += 1
            
            if verified_count % 10 == 0:
                print(f"Verified {verified_count} APIs...")
        
        # Save verification results
        output_file = os.path.join(self.output_dir, "phase3_verification_results.json")
        with open(output_file, 'w', encoding='utf-8') as f:
            json.dump(verification_results, f, indent=2, ensure_ascii=False)
        
        print(f"Phase 3 completed: Verified {verified_count} APIs")
        
        return {
            'total_apis_verified': verified_count,
            'validation_successful': sum(1 for r in verification_results 
                                       if r['validation_result']['is_valid']),
            'emulator_executed': sum(1 for r in verification_results 
                                   if r['emulator_result'] is not None),
            'verification_results': verification_results,
            'output_file': output_file
        }
    
    def _generate_final_report(self):
        """Generate final pipeline report"""
        print("\nGenerating Final Report")
        print("-" * 25)
        
        # Calculate statistics
        phase1_stats = self.results['phase1_results']
        phase2_stats = self.results['phase2_results']
        phase3_stats = self.results['phase3_results']
        
        # Validation statistics
        validation_stats = validation_agent.get_validation_statistics()
        
        # Emulator statistics
        emulator_stats = emulator_interface.get_test_statistics()
        
        # Generate report
        report = {
            'pipeline_summary': {
                'total_files_processed': phase1_stats['total_files_processed'],
                'total_methods_extracted': phase1_stats['total_methods_extracted'],
                'total_methods_analyzed': phase2_stats['total_methods_analyzed'],
                'permission_required_apis': phase2_stats['permission_required_count'],
                'apis_verified': phase3_stats['total_apis_verified'],
                'validation_success_rate': validation_stats.get('success_rate', 0),
                'emulator_success_rate': emulator_stats.get('success_rate', 0)
            },
            'phase1_results': phase1_stats,
            'phase2_results': phase2_stats,
            'phase3_results': phase3_stats,
            'validation_statistics': validation_stats,
            'emulator_statistics': emulator_stats,
            'timestamp': time.time()
        }
        
        # Save report
        report_file = os.path.join(self.output_dir, "final_pipeline_report.json")
        with open(report_file, 'w', encoding='utf-8') as f:
            json.dump(report, f, indent=2, ensure_ascii=False)
        
        # Print summary
        print(f"\nPipeline Summary:")
        print(f"- Files processed: {report['pipeline_summary']['total_files_processed']}")
        print(f"- Methods extracted: {report['pipeline_summary']['total_methods_extracted']}")
        print(f"- Methods analyzed: {report['pipeline_summary']['total_methods_analyzed']}")
        print(f"- APIs requiring permissions: {report['pipeline_summary']['permission_required_apis']}")
        print(f"- APIs verified: {report['pipeline_summary']['apis_verified']}")
        print(f"- Validation success rate: {report['pipeline_summary']['validation_success_rate']:.2%}")
        print(f"- Emulator success rate: {report['pipeline_summary']['emulator_success_rate']:.2%}")
        
        print(f"\nFinal report saved to: {report_file}")
    
    def add_custom_demonstration_case(self, example_code: str, result: str, description: str = ""):
        """Add custom demonstration case for LLM learning"""
        add_demonstration_case(example_code, result, description)
        print(f"Added custom demonstration case: {description}")
    
    def export_all_results(self, export_dir: str = "export"):
        """Export all pipeline results"""
        os.makedirs(export_dir, exist_ok=True)
        
        # Export validation history
        validation_agent.export_validation_history(
            os.path.join(export_dir, "validation_history.json")
        )
        
        # Export emulator results
        emulator_interface.export_test_results(
            os.path.join(export_dir, "emulator_results.json")
        )
        
        # Export pipeline results
        pipeline_file = os.path.join(export_dir, "complete_pipeline_results.json")
        with open(pipeline_file, 'w', encoding='utf-8') as f:
            json.dump(self.results, f, indent=2, ensure_ascii=False)
        
        print(f"All results exported to: {export_dir}")


def main():
    """Main function to run the complete pipeline"""
    # Example usage
    sdk_path = "android-sdk-sources-for-api-level-35-master"  # Adjust path as needed
    pipeline = LLMPermPipeline(sdk_path)
    
    # Run complete pipeline with limited APIs for testing
    results = pipeline.run_complete_pipeline(sdk_version=35, max_apis=100)
    
    # Export results
    pipeline.export_all_results()
    
    print("\nPipeline execution completed successfully!")


if __name__ == "__main__":
    main() 