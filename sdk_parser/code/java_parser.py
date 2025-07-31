import javalang
import json
import os
import re


def extract_java_method_info_from_file(file_path):
    try:
        # Read the Java code from the specified file
        with open(file_path, 'r', encoding='utf-8') as file:
            java_code = file.read()

        # Parse the Java code into an AST
        tree = javalang.parse.parse(java_code)

        # Initialize the list to store method information for each method in the file
        methods_info = []

        # Extract class context information
        class_info = extract_class_context(tree, file_path)

        # Iterate over all method declarations in the class body
        for _, method_declaration in tree.filter(javalang.tree.MethodDeclaration):
            method_info = {}

            # Extract Javadoc
            method_info['javadoc'] = method_declaration.documentation if method_declaration.documentation else "No Javadoc"

            # Extract annotations with enhanced parsing
            method_info['annotations'] = extract_annotations(method_declaration.annotations)

            # Extract method signature (including modifiers)
            method_info['signature'] = f"{method_declaration.modifiers} {method_declaration.return_type} {method_declaration.name}"

            # Extract method name separately
            method_info['method_name'] = method_declaration.name

            # Extract parameters
            method_info['parameters'] = [{'type': param.type.name, 'name': param.name} for param in method_declaration.parameters]

            # Extract method body as a string
            method_info['body'] = str(method_declaration.body)

            # Extract contextual information as per paper requirements
            method_info['contextual_info'] = extract_contextual_information(
                method_declaration, class_info, java_code
            )

            # Append method info to the methods list
            methods_info.append(method_info)

        return methods_info

    except javalang.parser.JavaSyntaxError as jse:
        print(f"Syntax error parsing Java file {file_path}: {jse}")
        # Fallback to keyword matching
        return extract_methods_with_keyword_matching(file_path)
    except FileNotFoundError:
        print(f"File not found: {file_path}")
    except Exception as e:
        print(f"An error occurred while processing {file_path}: {e}")
    return None


def extract_class_context(tree, file_path):
    """Extract class context information as per paper requirements"""
    class_info = {
        'class_name': '',
        'package_name': '',
        'class_modifiers': [],
        'is_deprecated': False,
        'api_level': None
    }
    
    # Extract package information
    for _, package_decl in tree.filter(javalang.tree.PackageDeclaration):
        class_info['package_name'] = '.'.join(package_decl.name.split('.'))
        break
    
    # Extract class information
    for _, class_decl in tree.filter(javalang.tree.ClassDeclaration):
        class_info['class_name'] = class_decl.name
        class_info['class_modifiers'] = list(class_decl.modifiers) if class_decl.modifiers else []
        
        # Check for @Deprecated annotation
        if class_decl.annotations:
            for annotation in class_decl.annotations:
                if annotation.name == 'Deprecated':
                    class_info['is_deprecated'] = True
                    break
        
        # Extract API level from file path or annotations
        class_info['api_level'] = extract_api_level_from_path(file_path)
        break
    
    return class_info


def extract_annotations(annotations):
    """Enhanced annotation extraction as per paper requirements"""
    if not annotations:
        return []
    
    extracted_annotations = []
    for anno in annotations:
        annotation_info = {
            'name': anno.name,
            'arguments': {}
        }
        
        # Extract annotation arguments
        if anno.element:
            for arg in anno.element:
                if hasattr(arg, 'element') and hasattr(arg, 'value'):
                    annotation_info['arguments'][arg.element] = arg.value
        
        extracted_annotations.append(annotation_info)
    
    return extracted_annotations


def extract_contextual_information(method_declaration, class_info, java_code):
    """Extract contextual information as specified in the paper"""
    contextual_info = {
        'api_level': class_info['api_level'],
        'deprecation_status': extract_deprecation_status(method_declaration),
        'method_documentation': extract_method_documentation(method_declaration),
        'class_context': {
            'class_name': class_info['class_name'],
            'package_name': class_info['package_name'],
            'class_modifiers': class_info['class_modifiers']
        },
        'annotations': extract_annotations(method_declaration.annotations),
        'permission_indicators': extract_permission_indicators(method_declaration, java_code)
    }
    
    return contextual_info


def extract_deprecation_status(method_declaration):
    """Extract deprecation status and replacement suggestions"""
    deprecation_info = {
        'is_deprecated': False,
        'replacement_suggestion': None,
        'since_version': None
    }
    
    if method_declaration.annotations:
        for annotation in method_declaration.annotations:
            if annotation.name == 'Deprecated':
                deprecation_info['is_deprecated'] = True
                
                # Try to extract replacement information from JavaDoc
                if method_declaration.documentation:
                    doc_text = method_declaration.documentation
                    # Look for @deprecated tag
                    deprecated_match = re.search(r'@deprecated\s+(.*?)(?:\n|$)', doc_text, re.IGNORECASE)
                    if deprecated_match:
                        deprecation_info['replacement_suggestion'] = deprecated_match.group(1).strip()
                
                break
    
    return deprecation_info


def extract_method_documentation(method_declaration):
    """Extract method documentation including JavaDoc and inline comments"""
    documentation = {
        'javadoc': method_declaration.documentation if method_declaration.documentation else None,
        'inline_comments': [],
        'param_descriptions': {},
        'return_description': None,
        'throws_descriptions': {}
    }
    
    if method_declaration.documentation:
        doc_text = method_declaration.documentation
        
        # Extract parameter descriptions
        param_matches = re.findall(r'@param\s+(\w+)\s+(.*?)(?=@|\n|$)', doc_text, re.DOTALL)
        for param_name, description in param_matches:
            documentation['param_descriptions'][param_name] = description.strip()
        
        # Extract return description
        return_match = re.search(r'@return\s+(.*?)(?=@|\n|$)', doc_text, re.DOTALL)
        if return_match:
            documentation['return_description'] = return_match.group(1).strip()
        
        # Extract throws descriptions
        throws_matches = re.findall(r'@throws\s+(\w+)\s+(.*?)(?=@|\n|$)', doc_text, re.DOTALL)
        for exception_name, description in throws_matches:
            documentation['throws_descriptions'][exception_name] = description.strip()
    
    return documentation


def extract_permission_indicators(method_declaration, java_code):
    """Extract permission-related indicators from method"""
    indicators = {
        'requires_permission_annotation': False,
        'permission_keywords': [],
        'system_service_calls': [],
        'hardware_access_indicators': []
    }
    
    # Check for @RequiresPermission annotation
    if method_declaration.annotations:
        for annotation in method_declaration.annotations:
            if annotation.name in ['RequiresPermission', 'RequiresPermissions']:
                indicators['requires_permission_annotation'] = True
                break
    
    # Extract permission-related keywords from method body
    method_body = str(method_declaration.body)
    
    # Permission-related keywords as per paper
    permission_keywords = ['getSystemService', 'checkSelfPermission', 'requestPermissions', 
                          'ACCESS_FINE_LOCATION', 'ACCESS_COARSE_LOCATION', 'INTERNET',
                          'READ_EXTERNAL_STORAGE', 'WRITE_EXTERNAL_STORAGE', 'CAMERA',
                          'RECORD_AUDIO', 'READ_CONTACTS', 'WRITE_CONTACTS']
    
    for keyword in permission_keywords:
        if keyword in method_body:
            indicators['permission_keywords'].append(keyword)
    
    # System service calls
    system_services = ['LOCATION_SERVICE', 'CONNECTIVITY_SERVICE', 'CAMERA_SERVICE',
                      'AUDIO_SERVICE', 'STORAGE_SERVICE', 'TELEPHONY_SERVICE']
    
    for service in system_services:
        if service in method_body:
            indicators['system_service_calls'].append(service)
    
    # Hardware access indicators
    hardware_indicators = ['LocationManager', 'ConnectivityManager', 'Camera', 
                          'AudioManager', 'TelephonyManager', 'WifiManager']
    
    for indicator in hardware_indicators:
        if indicator in method_body:
            indicators['hardware_access_indicators'].append(indicator)
    
    return indicators


def extract_api_level_from_path(file_path):
    """Extract API level from file path"""
    # Look for API level in path (e.g., android-sdk-sources-for-api-level-35-master)
    api_match = re.search(r'api-level-(\d+)', file_path)
    if api_match:
        return int(api_match.group(1))
    return None


def extract_methods_with_keyword_matching(java_code):
    """Keyword matching approach as fallback when AST parsing fails"""
    methods_info = []
    
    # Keywords for method identification as per paper
    access_modifiers = ['public', 'protected', 'private']
    return_types = ['void', 'int', 'String', 'boolean', 'long', 'float', 'double', 'Object']
    method_keywords = ['get', 'set', 'create', 'request', 'manage', 'check', 'is', 'has']
    
    # Simple regex-based method extraction
    method_pattern = r'(public|protected|private)\s+(\w+(?:<[^>]+>)?)\s+(\w+)\s*\([^)]*\)\s*\{'
    
    matches = re.finditer(method_pattern, java_code)
    for match in matches:
        modifier, return_type, method_name = match.groups()
        
        method_info = {
            'method_name': method_name,
            'signature': f"{modifier} {return_type} {method_name}",
            'javadoc': 'No Javadoc (keyword matching)',
            'annotations': [],
            'parameters': [],
            'body': 'Body not extracted (keyword matching)',
            'contextual_info': {
                'api_level': None,
                'deprecation_status': {'is_deprecated': False},
                'method_documentation': {'javadoc': None},
                'class_context': {},
                'annotations': [],
                'permission_indicators': {'permission_keywords': []}
            }
        }
        
        # Check for permission-related keywords in method name
        for keyword in method_keywords:
            if keyword.lower() in method_name.lower():
                method_info['contextual_info']['permission_indicators']['permission_keywords'].append(keyword)
        
        methods_info.append(method_info)
    
    return methods_info


def find_java_files(directory):
    """
    Recursively search the directory for all Java files.

    Parameters:
    - directory (str): The path to the directory where the search will begin.

    Returns:
    - list: A list of paths to Java files.
    """
    java_files = []
    for root, dirs, files in os.walk(directory):
        for file in files:
            if file.endswith(".java"):
                java_files.append(os.path.join(root, file))
    return java_files


def save_extraction_results(data, output_file_path):
    """
    Save a list of extracted Java method information to a JSON file.

    Parameters:
    - data (list): A list of dictionaries, each containing the Java file path and method information.
    - output_file_path (str): The path where the JSON file will be saved.
    """

    def clean_string(s):
        return s.encode('utf-8', 'ignore').decode('utf-8', 'ignore')

    def clean_data(data):
        if isinstance(data, dict):
            return {k: clean_data(v) for k, v in data.items()}
        elif isinstance(data, list):
            return [clean_data(i) for i in data]
        elif isinstance(data, str):
            return clean_string(data)
        return data

    data = clean_data(data)

    with open(output_file_path, 'w', encoding='utf-8') as f:
        json.dump(data, f, indent=4, ensure_ascii=False)


def unit_test():
    # Example usage
    file_path = "/Users/huhan/PycharmProjects/AndroidSDKParse/sdk_parser/android-sdk-sources-for-api-level-35-master/android/content/pm/PackageInstaller.java"  # Replace this with the path to your Java file

    # Extract method information
    methods_info = extract_java_method_info_from_file(file_path)
    print(methods_info)


def batch_test():
    # Example usage
    directory_path = r"/Users/huhan/PycharmProjects/AndroidSDKParse/sdk_parser/android-sdk-sources-for-api-level-24-master"  # Replace with the path to your directory
    java_file_paths = find_java_files(directory_path)
    # print(java_file_paths)

    all_data = []
    for file in java_file_paths:
        print(file)
        methods_info = extract_java_method_info_from_file(file)
        all_data.append({
            'java_file_path': file,
            'methods_info': methods_info
        })

    output_file_path = r'/Users/huhan/PycharmProjects/AndroidSDKParse/sdk_parser/baselines/sdk24/SDK24.json'
    save_extraction_results(all_data, output_file_path)


def parse_json():
    output_file_path = r'E:\research\smu\smu mac\files\PycharmProjects\AndroidSDKParse\sdk_parser\baselines\sdk24\SDK24.json'
    with open(output_file_path, 'r', encoding='UTF-8') as file:
        json_array = json.load(file)

    # Get the number of elements in the array
    num_elements = len(json_array)

    print(f"Number of elements in the JSON array: {num_elements}")
    # 9406


if __name__ == '__main__':
    # unit_test()
    # batch_test()
    parse_json()


