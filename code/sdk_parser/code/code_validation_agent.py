"""
Code Validation Agent for LLMPerm
Validates test cases for self-containment and SDK version alignment
"""

import re
import json
from typing import Dict, List, Tuple, Optional
from llm_parser import contact_chatGPT


class CodeValidationAgent:
    """Code Validation Agent as described in the paper"""
    
    def __init__(self, model_id: str = "gpt-4o-mini"):
        self.model_id = model_id
        self.validation_history = []
    
    def validate_test_case(self, test_case_content: str, target_api: str, 
                          sdk_version: int, max_iterations: int = 3) -> Dict:
        """
        Validate a test case for self-containment and SDK version alignment
        Args:
            test_case_content: The test case code to validate
            target_api: The target API being tested
            sdk_version: The Android SDK version being tested
            max_iterations: Maximum number of refinement iterations
        Returns:
            Validation result with status and feedback
        """
        validation_result = {
            'is_valid': False,
            'feedback': [],
            'refined_content': None,
            'iterations': 0,
            'final_status': 'failed'
        }
        
        for iteration in range(max_iterations):
            validation_result['iterations'] = iteration + 1
            
            # Perform validation checks
            checks = self._perform_validation_checks(test_case_content, target_api, sdk_version)
            
            # Check if all validation checks pass
            if all(check['passed'] for check in checks):
                validation_result['is_valid'] = True
                validation_result['final_status'] = 'success'
                validation_result['refined_content'] = test_case_content
                break
            
            # Generate feedback for failed checks
            feedback = self._generate_validation_feedback(checks, test_case_content, target_api)
            validation_result['feedback'].append(feedback)
            
            # Try to refine the test case
            refined_content = self._refine_test_case(test_case_content, feedback, target_api, sdk_version)
            
            if refined_content and refined_content != test_case_content:
                test_case_content = refined_content
                validation_result['refined_content'] = refined_content
            else:
                # No improvement made, stop iteration
                break
        
        # Record validation history
        self.validation_history.append({
            'target_api': target_api,
            'sdk_version': sdk_version,
            'result': validation_result
        })
        
        return validation_result
    
    def _perform_validation_checks(self, test_case_content: str, target_api: str, 
                                  sdk_version: int) -> List[Dict]:
        """Perform all validation checks"""
        checks = [
            self._check_self_containment(test_case_content),
            self._check_sdk_version_compatibility(test_case_content, sdk_version),
            self._check_syntax_correctness(test_case_content),
            self._check_api_usage(test_case_content, target_api),
            self._check_permission_handling(test_case_content)
        ]
        return checks
    
    def _check_self_containment(self, test_case_content: str) -> Dict:
        """Check if test case is self-contained"""
        check_result = {
            'name': 'self_containment',
            'passed': True,
            'issues': []
        }
        
        # Check for missing imports
        required_imports = [
            'import android.content.Context;',
            'import android.Manifest;',
            'import android.content.pm.PackageManager;'
        ]
        
        for required_import in required_imports:
            if required_import not in test_case_content:
                check_result['passed'] = False
                check_result['issues'].append(f"Missing import: {required_import}")
        
        # Check for external dependencies
        external_deps = ['import com.example.', 'import org.example.']
        for dep in external_deps:
            if dep in test_case_content:
                check_result['passed'] = False
                check_result['issues'].append(f"External dependency found: {dep}")
        
        return check_result
    
    def _check_sdk_version_compatibility(self, test_case_content: str, sdk_version: int) -> Dict:
        """Check SDK version compatibility"""
        check_result = {
            'name': 'sdk_version_compatibility',
            'passed': True,
            'issues': []
        }
        
        # Check for API level annotations
        api_level_pattern = r'@RequiresApi\(api\s*=\s*(\d+)\)'
        api_matches = re.findall(api_level_pattern, test_case_content)
        
        for api_level in api_matches:
            if int(api_level) > sdk_version:
                check_result['passed'] = False
                check_result['issues'].append(f"API requires level {api_level}, but testing with {sdk_version}")
        
        # Check for deprecated APIs that might not be available
        deprecated_apis = [
            'getExternalStorageDirectory',  # Deprecated in API 29
            'requestPermissions',  # Deprecated in favor of registerForActivityResult
        ]
        
        for deprecated_api in deprecated_apis:
            if deprecated_api in test_case_content and sdk_version >= 29:
                check_result['issues'].append(f"Using deprecated API: {deprecated_api}")
        
        return check_result
    
    def _check_syntax_correctness(self, test_case_content: str) -> Dict:
        """Check basic syntax correctness"""
        check_result = {
            'name': 'syntax_correctness',
            'passed': True,
            'issues': []
        }
        
        # Check for basic Java syntax
        syntax_checks = [
            (r'public\s+class\s+\w+', "Missing public class declaration"),
            (r'public\s+void\s+test\w*\(', "Missing test method declaration"),
            (r'\{.*\}', "Missing braces"),
        ]
        
        for pattern, error_msg in syntax_checks:
            if not re.search(pattern, test_case_content, re.DOTALL):
                check_result['passed'] = False
                check_result['issues'].append(error_msg)
        
        return check_result
    
    def _check_api_usage(self, test_case_content: str, target_api: str) -> Dict:
        """Check if the target API is properly used"""
        check_result = {
            'name': 'api_usage',
            'passed': True,
            'issues': []
        }
        
        # Extract API class and method names
        api_parts = target_api.split('.')
        if len(api_parts) >= 2:
            api_class = api_parts[-2]  # Last part before method
            api_method = api_parts[-1]  # Method name
            
            # Check if API class is used
            if api_class not in test_case_content:
                check_result['passed'] = False
                check_result['issues'].append(f"Target API class '{api_class}' not found in test case")
            
            # Check if API method is called
            if api_method not in test_case_content:
                check_result['passed'] = False
                check_result['issues'].append(f"Target API method '{api_method}' not called in test case")
        
        return check_result
    
    def _check_permission_handling(self, test_case_content: str) -> Dict:
        """Check if permission handling is properly implemented"""
        check_result = {
            'name': 'permission_handling',
            'passed': True,
            'issues': []
        }
        
        # Check for permission-related code
        permission_indicators = [
            'checkSelfPermission',
            'requestPermissions',
            'onRequestPermissionsResult',
            'PERMISSION_GRANTED'
        ]
        
        permission_found = any(indicator in test_case_content for indicator in permission_indicators)
        
        if not permission_found:
            check_result['issues'].append("No permission handling code found")
            # This is a warning, not a failure
        
        return check_result
    
    def _generate_validation_feedback(self, checks: List[Dict], test_case_content: str, 
                                    target_api: str) -> str:
        """Generate feedback for failed validation checks"""
        failed_checks = [check for check in checks if not check['passed']]
        
        if not failed_checks:
            return "All validation checks passed."
        
        feedback_prompt = f"""
As a Code Validation Agent, analyze the following test case and provide specific feedback for improvement.

Target API: {target_api}

Test Case:
{test_case_content}

Validation Issues:
"""
        
        for check in failed_checks:
            feedback_prompt += f"\n- {check['name']}: {', '.join(check['issues'])}"
        
        feedback_prompt += """

Provide specific, actionable feedback to fix these issues. Focus on:
1. How to make the test case self-contained
2. How to ensure SDK version compatibility
3. How to properly use the target API
4. How to handle permissions correctly

Return only the feedback, no explanations.
"""
        
        try:
            feedback = contact_chatGPT(self.model_id, feedback_prompt)
            return feedback if feedback else "Validation failed but no specific feedback generated."
        except Exception as e:
            return f"Error generating feedback: {e}"
    
    def _refine_test_case(self, test_case_content: str, feedback: str, 
                          target_api: str, sdk_version: int) -> Optional[str]:
        """Refine test case based on validation feedback"""
        refinement_prompt = f"""
As a Code Generator, refine the following test case based on the validation feedback.

Target API: {target_api}
SDK Version: {sdk_version}

Original Test Case:
{test_case_content}

Validation Feedback:
{feedback}

Generate a refined test case that addresses all the issues mentioned in the feedback.
The test case should be:
1. Self-contained (no external dependencies)
2. Compatible with SDK version {sdk_version}
3. Properly use the target API
4. Handle permissions correctly

Return only the refined Java code, no explanations.
"""
        
        try:
            refined_content = contact_chatGPT(self.model_id, refinement_prompt)
            return refined_content if refined_content else None
        except Exception as e:
            print(f"Error refining test case: {e}")
            return None
    
    def get_validation_statistics(self) -> Dict:
        """Get validation statistics from history"""
        if not self.validation_history:
            return {}
        
        total_validations = len(self.validation_history)
        successful_validations = sum(1 for v in self.validation_history 
                                   if v['result']['final_status'] == 'success')
        
        return {
            'total_validations': total_validations,
            'successful_validations': successful_validations,
            'success_rate': successful_validations / total_validations if total_validations > 0 else 0,
            'average_iterations': sum(v['result']['iterations'] for v in self.validation_history) / total_validations
        }
    
    def export_validation_history(self, filename: str = "validation_history.json"):
        """Export validation history to file"""
        try:
            with open(filename, 'w', encoding='utf-8') as f:
                json.dump(self.validation_history, f, indent=2, ensure_ascii=False)
            print(f"Validation history exported to {filename}")
        except Exception as e:
            print(f"Error exporting validation history: {e}")


# Global validation agent instance
validation_agent = CodeValidationAgent() 