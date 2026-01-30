"""
Emulator Interface for LLMPerm
Provides interface for executing test cases on Android emulator and validating permission predictions
"""

import subprocess
import time
import re
import json
import os
from typing import Dict, List, Optional, Tuple
from abc import ABC, abstractmethod


class EmulatorInterface(ABC):
    """Abstract interface for Android emulator operations"""
    
    @abstractmethod
    def start_emulator(self, avd_name: str) -> bool:
        """Start Android emulator"""
        pass
    
    @abstractmethod
    def install_app(self, apk_path: str) -> bool:
        """Install APK on emulator"""
        pass
    
    @abstractmethod
    def run_test_case(self, test_case_content: str, target_api: str) -> Dict:
        """Run test case on emulator"""
        pass
    
    @abstractmethod
    def check_permission_exception(self, log_output: str) -> Dict:
        """Check for permission-related exceptions in logs"""
        pass


class ADBEmulatorInterface(EmulatorInterface):
    """ADB-based emulator interface"""
    
    def __init__(self, adb_path: str = "adb", emulator_path: str = "emulator"):
        self.adb_path = adb_path
        self.emulator_path = emulator_path
        self.device_id = None
        self.test_results = []
    
    def start_emulator(self, avd_name: str) -> bool:
        """Start Android emulator using ADB"""
        try:
            # Start emulator
            cmd = [self.emulator_path, "-avd", avd_name, "-no-snapshot-load"]
            subprocess.Popen(cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
            
            # Wait for emulator to boot
            print("Waiting for emulator to boot...")
            time.sleep(30)  # Adjust based on emulator boot time
            
            # Get device ID
            result = subprocess.run([self.adb_path, "devices"], 
                                  capture_output=True, text=True)
            
            devices = result.stdout.strip().split('\n')[1:]  # Skip header
            for device in devices:
                if device.strip() and 'emulator' in device:
                    self.device_id = device.split('\t')[0]
                    print(f"Emulator started with device ID: {self.device_id}")
                    return True
            
            print("No emulator device found")
            return False
            
        except Exception as e:
            print(f"Error starting emulator: {e}")
            return False
    
    def install_app(self, apk_path: str) -> bool:
        """Install APK on emulator using ADB"""
        if not self.device_id:
            print("No emulator device available")
            return False
        
        try:
            cmd = [self.adb_path, "-s", self.device_id, "install", apk_path]
            result = subprocess.run(cmd, capture_output=True, text=True)
            
            if "Success" in result.stdout:
                print(f"APK installed successfully: {apk_path}")
                return True
            else:
                print(f"Failed to install APK: {result.stderr}")
                return False
                
        except Exception as e:
            print(f"Error installing APK: {e}")
            return False
    
    def run_test_case(self, test_case_content: str, target_api: str) -> Dict:
        """Run test case on emulator and capture results"""
        if not self.device_id:
            return {"success": False, "error": "No emulator device available"}
        
        try:
            # Create test app from test case content
            test_app_info = self._create_test_app(test_case_content, target_api)
            
            if not test_app_info["success"]:
                return test_app_info
            
            # Install test app
            if not self.install_app(test_app_info["apk_path"]):
                return {"success": False, "error": "Failed to install test app"}
            
            # Run test and capture logs
            log_output = self._run_test_and_capture_logs(test_app_info["package_name"])
            
            # Analyze results
            result = self._analyze_test_results(log_output, target_api)
            
            # Clean up
            self._cleanup_test_app(test_app_info["package_name"])
            
            return result
            
        except Exception as e:
            return {"success": False, "error": f"Error running test case: {e}"}
    
    def check_permission_exception(self, log_output: str) -> Dict:
        """Check for permission-related exceptions in logs"""
        permission_exceptions = {
            'found': False,
            'exception_type': None,
            'permission_required': None,
            'message': None
        }
        
        # Common permission exception patterns
        exception_patterns = [
            r'SecurityException.*permission.*denied',
            r'SecurityException.*requires.*permission',
            r'Permission.*denied.*for.*operation',
            r'requires.*permission.*(\w+\.\w+)',
            r'permission.*(\w+\.\w+).*not.*granted'
        ]
        
        for pattern in exception_patterns:
            matches = re.findall(pattern, log_output, re.IGNORECASE)
            if matches:
                permission_exceptions['found'] = True
                permission_exceptions['exception_type'] = 'SecurityException'
                permission_exceptions['message'] = matches[0]
                
                # Extract permission name if found
                permission_match = re.search(r'(\w+\.\w+)', matches[0])
                if permission_match:
                    permission_exceptions['permission_required'] = permission_match.group(1)
                break
        
        return permission_exceptions
    
    def _create_test_app(self, test_case_content: str, target_api: str) -> Dict:
        """Create test Android app from test case content"""
        # This is a simplified implementation
        # In practice, this would use Android build tools to create a proper APK
        
        try:
            # Create temporary directory for test app
            test_dir = f"test_app_{target_api.replace('.', '_')}"
            os.makedirs(test_dir, exist_ok=True)
            
            # Generate Android project structure
            self._generate_android_project(test_dir, test_case_content, target_api)
            
            # Build APK (simplified - in practice would use Gradle)
            apk_path = self._build_test_apk(test_dir)
            
            return {
                "success": True,
                "apk_path": apk_path,
                "package_name": f"com.test.{target_api.replace('.', '_')}",
                "test_dir": test_dir
            }
            
        except Exception as e:
            return {"success": False, "error": f"Error creating test app: {e}"}
    
    def _generate_android_project(self, test_dir: str, test_case_content: str, target_api: str):
        """Generate Android project structure"""
        # Create basic Android project structure
        os.makedirs(f"{test_dir}/src/main/java/com/test", exist_ok=True)
        os.makedirs(f"{test_dir}/src/main/res/layout", exist_ok=True)
        
        # Create AndroidManifest.xml
        manifest_content = f"""<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.test.{target_api.replace('.', '_')}">
    
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    
    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/AppTheme">
        
        <activity android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>"""
        
        with open(f"{test_dir}/src/main/AndroidManifest.xml", 'w') as f:
            f.write(manifest_content)
        
        # Create MainActivity.java
        activity_content = f"""package com.test.{target_api.replace('.', '_')};

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {{
    private static final String TAG = "TestApp";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Run the test case
        runTest();
    }}
    
    private void runTest() {{
        try {{
            {test_case_content}
            Log.i(TAG, "Test case executed successfully");
        }} catch (Exception e) {{
            Log.e(TAG, "Test case failed: " + e.getMessage(), e);
        }}
    }}
}}"""
        
        with open(f"{test_dir}/src/main/java/com/test/MainActivity.java", 'w') as f:
            f.write(activity_content)
    
    def _build_test_apk(self, test_dir: str) -> str:
        """Build test APK (simplified implementation)"""
        # This is a placeholder implementation
        # In practice, this would use Gradle or other build tools
        
        apk_path = f"{test_dir}/app-debug.apk"
        
        # Create a dummy APK file for demonstration
        with open(apk_path, 'w') as f:
            f.write("Dummy APK content")
        
        return apk_path
    
    def _run_test_and_capture_logs(self, package_name: str) -> str:
        """Run test app and capture logs"""
        if not self.device_id:
            return ""
        
        try:
            # Clear logs
            subprocess.run([self.adb_path, "-s", self.device_id, "logcat", "-c"])
            
            # Start the app
            subprocess.run([self.adb_path, "-s", self.device_id, "shell", 
                          "am", "start", "-n", f"{package_name}/.MainActivity"])
            
            # Wait for app to run
            time.sleep(5)
            
            # Capture logs
            result = subprocess.run([self.adb_path, "-s", self.device_id, "logcat", "-d"], 
                                  capture_output=True, text=True)
            
            return result.stdout
            
        except Exception as e:
            print(f"Error capturing logs: {e}")
            return ""
    
    def _cleanup_test_app(self, package_name: str):
        """Clean up test app from emulator"""
        if not self.device_id:
            return
        
        try:
            # Uninstall test app
            subprocess.run([self.adb_path, "-s", self.device_id, "uninstall", package_name])
        except Exception as e:
            print(f"Error cleaning up test app: {e}")
    
    def _analyze_test_results(self, log_output: str, target_api: str) -> Dict:
        """Analyze test results and determine permission requirements"""
        # Check for permission exceptions
        permission_check = self.check_permission_exception(log_output)
        
        # Check for successful execution
        success_indicators = [
            "Test case executed successfully",
            "Test passed",
            "No exceptions"
        ]
        
        execution_success = any(indicator in log_output for indicator in success_indicators)
        
        result = {
            "target_api": target_api,
            "execution_success": execution_success,
            "permission_exception": permission_check,
            "log_output": log_output[:1000],  # Truncate for storage
            "timestamp": time.time()
        }
        
        # Determine if API requires permissions
        if permission_check['found']:
            result["requires_permissions"] = True
            result["permission_required"] = permission_check['permission_required']
        elif execution_success:
            result["requires_permissions"] = False
        else:
            result["requires_permissions"] = "unknown"
        
        # Store result
        self.test_results.append(result)
        
        return result
    
    def get_test_statistics(self) -> Dict:
        """Get statistics from test results"""
        if not self.test_results:
            return {}
        
        total_tests = len(self.test_results)
        successful_tests = sum(1 for r in self.test_results if r["execution_success"])
        permission_required = sum(1 for r in self.test_results if r.get("requires_permissions") == True)
        permission_not_required = sum(1 for r in self.test_results if r.get("requires_permissions") == False)
        
        return {
            "total_tests": total_tests,
            "successful_tests": successful_tests,
            "success_rate": successful_tests / total_tests if total_tests > 0 else 0,
            "permission_required_count": permission_required,
            "permission_not_required_count": permission_not_required,
            "permission_required_rate": permission_required / total_tests if total_tests > 0 else 0
        }
    
    def export_test_results(self, filename: str = "emulator_test_results.json"):
        """Export test results to file"""
        try:
            with open(filename, 'w', encoding='utf-8') as f:
                json.dump(self.test_results, f, indent=2, ensure_ascii=False)
            print(f"Test results exported to {filename}")
        except Exception as e:
            print(f"Error exporting test results: {e}")


# Global emulator interface instance
emulator_interface = ADBEmulatorInterface() 