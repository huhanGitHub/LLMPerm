from difflib import SequenceMatcher


def string_similarity(str1, str2):
    """
    Calculate the string similarity between two strings using SequenceMatcher.

    Parameters:
    - str1: First string.
    - str2: Second string.

    Returns:
    - Similarity ratio (float between 0 and 1).
    """

    return SequenceMatcher(None, str1, str2).ratio()


def handle():
    path = r'../statistics/selected_apis.txt'
    pkgs = {}
    with open(path, 'r') as f:
        lines = f.readlines()
        for line in lines:
            pkg = line.split('/')[0]
            pkgs[pkg] = pkgs.get(pkg, 0) + 1
    sorted_pkgs = dict(sorted(pkgs.items(), key=lambda item: item[1]))
    for k, v in sorted_pkgs.items():
        print(k, v)


def compare_results():
    f1 = r'../statistics/selected_apis.txt'
    f2 = r'/Users/huhan/PycharmProjects/AndroidSDKParse/sdk_parser/baselines/AOSP_7.txt'
    f1_list = []
    f2_list = []
    with open(f1, 'r') as f:
        lines = f.readlines()
        for line in lines:
            line = line.split(',')[:2]
            line = ','.join(line)
            f1_list.append(line)

    with open(f2, 'r') as f2:
        lines2 = f2.readlines()
        for line in lines2:
            if line.startswith('L'):
                f2_list.append(line)

    save_list = []
    for index, i in enumerate(f1_list):
        print(index)
        class_name1, method_name1 = extract_class_and_method_our(i)
        for j in f2_list:
            class_name2, method_name2 = extract_outer_class_and_method_aosp(j)
            class_similarity = string_similarity(class_name1, class_name2)
            method_similarity = string_similarity(method_name1, method_name2)
            if class_similarity > 0.69 and method_similarity > 0.69:
                save_list.append(class_name1 + ',' + class_name2 + ',' + method_name1 + ',' + method_name2)
                print(class_name1, class_name2, method_name1, method_name2, class_similarity, method_similarity)


    save_file = r'../statistics/matched.txt'
    with open(save_file, 'w') as f:
        for index, line in enumerate(save_list):
            f.writelines(str(index) + "," + line + '\n')

def extract_outer_class_and_method_aosp(api):
    """
    Extract the outer class and method name from a given API string.

    Parameters:
    - api: The API string to process (starting with 'Lcom' or 'Landroid').

    Returns:
    - A tuple of (outer_class, method_name).
    """
    if not api.startswith(('Lcom', 'Landroid')):
        raise ValueError("API must start with 'Lcom' or 'Landroid'")

    # Remove the leading 'L' and normalize the API path
    api = api[1:]  # Remove 'L'
    class_and_method = api.split('(')[0]  # Remove method signature
    outer_class = class_and_method.split('$')[0]  # Extract outer class
    parts = class_and_method.rsplit('.', 1)  # Split class path and method name

    outer_class = outer_class.replace('/', '.')  # Normalize class path
    #outer_class = outer_class.split('.')[-1]
    method_name = parts[1] if len(parts) > 1 else None  # Extract method name
    return outer_class, method_name


def extract_class_and_method_our(api_result):
    """
    Extract the class name and method name from a given API result string.

    Parameters:
    - api_result: A string in the format 'path/to/Class.java,methodName'.

    Returns:
    - A tuple (class_name, method_name).
    """
    # Split the input string by ',' to separate the class path and method name
    class_path, method_name = api_result.split(',')

    # Remove the '.java' extension from the class path
    # class_name = class_path.split('/')[-1].replace('.java', '')  # Extract the last part as class name
    class_name = class_path.replace('.java', '')
    class_name = class_name.replace('/', '.')

    return class_name, method_name


import re


def parse_outer_class_and_method_ntdroid(signature):
    """
    Parse the outer class name (removing inner classes) and method name from the given string.

    Parameters:
    - signature: A string containing the class and method signature.

    Returns:
    - A tuple (outer_class_name, method_name).
    """
    # Regular expression to handle normal methods and constructors like <init>
    match = re.match(r"<([\w.$]+): [\w.$<>]+ (\w+|<init>)\(", signature)
    if match:
        full_class_name = match.group(1)  # Extract the full class name
        method_name = match.group(2)  # Extract the method name

        # Remove inner classes by splitting on `$` and keeping the first part
        outer_class_name = full_class_name.split('$')[0]

        return outer_class_name, method_name
    else:
        print(signature)
        raise ValueError("Invalid signature format")


def ntdroid():
    f2 = r'../baselines/API_29.txt'
    f1 = r'../statistics/selected_apis.txt'
    f1_list = []
    f2_list = []

    with open(f1, 'r') as f:
        lines = f.readlines()
        for line in lines:
            line = line.split(',')[:2]
            line = ','.join(line)
            f1_list.append(line)

    with open(f2, 'r') as f:
        lines = f.readlines()
        for line in lines:
            line = line.split('  ::  ')[0]
            outer_class_name, method_name = parse_outer_class_and_method_ntdroid(line)
            f2_list.append([outer_class_name, method_name])

    save_list = []
    for index, i in enumerate(f1_list):
        print(index)
        class_name1, method_name1 = extract_class_and_method_our(i)
        for j in f2_list:
            class_name2, method_name2 = j[0], j[1]
            class_similarity = string_similarity(class_name1, class_name2)
            method_similarity = string_similarity(method_name1, method_name2)
            if class_similarity > 0.69 and method_similarity > 0.69:
                save_list.append(class_name1 + ',' + class_name2 + ',' + method_name1 + ',' + method_name2)
                print(class_name1, class_name2, method_name1, method_name2, class_similarity, method_similarity)

    save_file = r'../statistics/matched2.txt'
    with open(save_file, 'w') as f:
        for index, line in enumerate(save_list):
            f.writelines(str(index) + "," + line + '\n')


if __name__=='__main__':
    handle()
    # compare_results()
    #ntdroid()