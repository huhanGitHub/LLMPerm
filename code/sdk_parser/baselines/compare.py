def print_sorted_dict(dictionary, dict_name, max_items=10):
    """
    Print a dictionary sorted by its values (count), with an option to limit the number of items.

    Parameters:
    - dictionary: The dictionary to sort and print.
    - dict_name: The name of the dictionary (for labeling).
    - max_items: The maximum number of items to print (default is 10).
    """
    print(f"\n{dict_name} sorted by count (showing up to {max_items} items):")
    sorted_items = sorted(dictionary.items(), key=lambda item: item[1], reverse=True)
    for key, value in sorted_items[:max_items]:
        print(f"{key}: {value}")


def compare():
    file1 = r'/Users/huhan/PycharmProjects/AndroidSDKParse/sdk_parser/baselines/sdk29/permission_api.txt'
    file2 = r'/Users/huhan/PycharmProjects/AndroidSDKParse/sdk_parser/permission_api.txt'
    lines1_path = {}
    lines2_path = {}

    with open(file1, 'r') as f1, open(file2, 'r') as f2:
        lines1 = f1.readlines()
        for line1 in lines1:
            line1 = line1.split(',')
            path = line1[0]
            path = path.replace('android-sdk-sources-for-api-level-29-master/', '')
            path = path.replace('.java', '')

            # match first directory
            first = path.split('/')[0]

            # filter out 'com'
            if first != 'com':
                continue

            path = path.replace('com/android/', '')
            path = path.split('/')[0]
            method = line1[1]
            lines1_path[path] = lines1_path.get(path, 0) + 1

        lines2 = f2.readlines()
        for line2 in lines2:
            line2 = line2.split(',')
            path = line2[0]
            path = path.replace('android-sdk-sources-for-api-level-35-master/', '')
            path = path.replace('.java', '')

            # match first directory
            first = path.split('/')[0]

            # filter out 'com'
            if first != 'com':
                continue

            path = path.replace('com/android/', '')
            path = path.split('/')[0]
            method = line1[1]
            lines2_path[path] = lines2_path.get(path, 0) + 1


    print_sorted_dict(lines1_path, 'sdk29')
    print_sorted_dict(lines2_path, 'sdk35')


# Function to extract file name and method name from a case
def extract_file_and_method(case):
    # Split the input case on commas to isolate components
    parts = case.split(",")
    file_path = parts[0].strip()
    method_name = parts[1].strip()

    # Extract the file name from the file path
    file_name = file_path.split("/")[-1] if "/" in file_path else file_path.split("\\")[-1]

    return file_name, method_name

def test():
    # Input cases
    # case_1 = "android-sdk-sources-for-api-level-24-master/com/android/nfc_extras/NfcAdapterExtras.java,getEmbeddedExecutionEnvironment,Yes, requires the WRITE_SECURE_SETTINGS permission."
    # case_2 = r"E:\\research\\smu\\smu mac\\files\\PycharmProjects\\AndroidSDKParse\\sdk_parser\\android-sdk-sources-for-api-level-23-master\\android\\view\\inputmethod\\InputMethodManager.java,setCurrentInputMethodSubtype,WRITE_SECURE_SETTINGS"

    # Extract data from both cases
    # file_1, method_1 = extract_file_and_method(case_1)
    # file_2, method_2 = extract_file_and_method(case_2)

    # # Print results
    # print("Case 1:")
    # print(f"File Name: {file_1}")
    # print(f"Method Name: {method_1}")
    #
    # print("\nCase 2:")
    # print(f"File Name: {file_2}")
    # print(f"Method Name: {method_2}")
    #
    file1 = r'E:\research\smu\smu mac\files\PycharmProjects\AndroidSDKParse\sdk_parser\baselines\javaDoc\api35.txt'
    file2 = r'E:\research\smu\smu mac\files\PycharmProjects\AndroidSDKParse\sdk_parser\permission_api.txt'

    with open(file1, 'r') as f1, open(file2, 'r') as f2:
        cases1 = f1.readlines()
        cases2 = f2.readlines()
        count = 0
        for case1 in cases1:
            file_1, method_1 = extract_file_and_method(case1)
            for case2 in cases2:
                file_2, method_2 = extract_file_and_method(case2)
                if file_1 == file_2 and method_1 == method_2:
                    count += 1
                    print(count)
                    break




if __name__ == '__main__':
    # compare()
    test()