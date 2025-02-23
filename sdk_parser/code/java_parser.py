import javalang
import json
import os


def extract_java_method_info_from_file(file_path):
    try:
        # Read the Java code from the specified file
        with open(file_path, 'r', encoding='utf-8') as file:
            java_code = file.read()

        # Parse the Java code into an AST
        tree = javalang.parse.parse(java_code)

        # Initialize the list to store method information for each method in the file
        methods_info = []

        # Iterate over all method declarations in the class body
        for _, method_declaration in tree.filter(javalang.tree.MethodDeclaration):
            method_info = {}

            # Extract Javadoc
            method_info['javadoc'] = method_declaration.documentation if method_declaration.documentation else "No Javadoc"

            # Extract annotations, checking for None in elements
            method_info['annotations'] = [
                {anno.name: {arg.element: arg.value for arg in (anno.element or [])} for anno in method_declaration.annotations}
            ]

            # Extract method signature (including modifiers)
            method_info['signature'] = f"{method_declaration.modifiers} {method_declaration.return_type} {method_declaration.name}"

            # Extract method name separately
            method_info['method_name'] = method_declaration.name

            # Extract parameters
            method_info['parameters'] = [{'type': param.type.name, 'name': param.name} for param in method_declaration.parameters]

            # Extract method body as a string
            method_info['body'] = str(method_declaration.body)

            # Append method info to the methods list
            methods_info.append(method_info)

        return methods_info

    except javalang.parser.JavaSyntaxError as jse:
        print(f"Syntax error parsing Java file {file_path}: {jse}")
    except FileNotFoundError:
        print(f"File not found: {file_path}")
    except Exception as e:
        print(f"An error occurred while processing {file_path}: {e}")
    return None


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


