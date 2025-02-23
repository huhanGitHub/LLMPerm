import re
import os
def extract_permission_methods(java_source):
    """
    Extracts methods annotated with @RequiresPermission and their specified permissions.

    Args:
    java_source (str): A string containing the Java source code.

    Returns:
    list of tuples: A list where each tuple contains the method signature and the permission type.
    """
    # Regular expression pattern to find @RequiresPermission and method signature
    pattern = r'@RequiresPermission\(([^)]+)\)\s+public\s+[\w<>\[\]]+\s+([\w]+)\s*\([^)]*\)\s*\{'
    results = re.findall(pattern, java_source)

    # List to hold the results
    extracted_data = []

    for permission, method_name in results:
        # Clean up the permission string
        permission = permission.strip()
        # Append the tuple of method name and permission to the results list
        extracted_data.append((method_name, permission))

    return extracted_data

# Example usage
java_code = """
@RequiresPermission(android.Manifest.permission.VIBRATE_ALWAYS_ON)
public boolean setAlwaysOnEffect(int uid, String opPkg, int alwaysOnId,
    @Nullable VibrationEffect effect, @Nullable VibrationAttributes attributes) {
    Log.w(TAG, "Always-on effects aren't supported");
    return false;
}
"""


def batch_extraction(directory, save_path = r'javaDoc.txt'):
    """
    Traverse all Java files in a specified directory and return their contents.

    Args:
    directory (str): The path to the directory containing Java files.

    Returns:
    dict: A dictionary where each key is the file path and the value is the content of the Java file.
    """
    # Dictionary to hold file paths and their contents
    java_files_content = {}
    results = []
    total_files = 0
    for root, dirs, files in os.walk(directory):
        for file_name in files:
            if file_name.endswith('.java'):
                total_files += 1
    processed_files = 0
    # Walk through the directory
    for root, dirs, files in os.walk(directory):
        # Find all .java files in the current directory
        for file_name in files:
            if file_name.endswith('.java'):
                file_path = os.path.join(root, file_name)
                # Read the content of the Java file
                with open(file_path, 'r', encoding='utf-8') as file:
                    java_files_content = file.read()
                    extracted_info = extract_permission_methods(java_files_content)
                    if extracted_info:
                        for i in extracted_info:
                            permissions = i[1].replace('\n', '')
                            mapping = file_path + ',' + i[0] + ',' + permissions
                            results.append(mapping)

            processed_files += 1
            print(f'Processing progress: {processed_files}/{total_files} files processed.')

    with open(save_path, 'w', encoding='utf-8') as file:
        for mapping in results:
            file.write(mapping + '\n')


if __name__ == '__main__':
    # Extract methods and permissions
    # extract_permission_methods(java_code)
    directory = r'E:\research\smu\smu mac\files\PycharmProjects\AndroidSDKParse\sdk_parser\android-sdk-sources-for-api-level-23-master'
    save_path = r'E:\research\smu\smu mac\files\PycharmProjects\AndroidSDKParse\sdk_parser\baselines\javaDoc\api23.txt'
    batch_extraction(directory, save_path)