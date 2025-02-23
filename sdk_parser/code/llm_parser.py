import difflib
import os
import re
import openai
from openai import OpenAI
import json


def contact_chatGPT(model, prompt):
    key = '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'
    client = OpenAI(api_key=key)
    response = ''
    try:
        stream = client.chat.completions.create(
            model=model,
            messages=[{"role": "user", "content": prompt}],
            stream=True
        )

        for chunk in stream:
            content = chunk.choices[0].delta.content
            if content is not None:
                if 'Yes' in content:
                    print(content, end="")
                response += content


    except openai.OpenAIError as e:
        print(f"An error occurred: {e}")
        return None

    except Exception as e:
        print(f"An unexpected error occurred: {e}")
        return None

    return response


def unit_test():
    model_id = "gpt-4o-mini"
    prompt_text = "This is a Java method code of Android SDK, please infer if this method requires any permissions. Reply simplely, if yes, reply 'Yes, permission1, permission2, ...'. If no, reply 'No'."
    method_code = "javadoc: /** this class encodes three bytes per atom. */, annotations: [{}], signature: {'protected'} BasicType(dimensions=[], name=int) bytesPerAtom, method_name: bytesPerAtom,parameters: [], body: [ReturnStatement(expression=Literal(postfix_operators=[], prefix_operators=[], qualifier=None, selectors=[], value=3), label=None)]"
    prompt = prompt_text + '\n' + method_code
    reply = contact_chatGPT(model_id, prompt)
    print(reply)


def method_permission_check(method_code):
    model_id = "gpt-4o-mini"
    prompt_text = ("This is a Java method code of Android SDK, please infer if this method requires any permissions. "
                   "Reply simple, if yes, reply 'Yes' + very short reasons. If no, reply 'No'")
    prompt = prompt_text + '\n' + method_code
    reply = contact_chatGPT(model_id, prompt)
    return reply


def batch_test():
    file_path = r'/Users/huhan/PycharmProjects/AndroidSDKParse/sdk_parser/SDK35.json'
    all_data = []
    counter = 1
    with open(file_path, 'r') as file:
        methods_data = json.load(file)
        # print(len(methods_data))
        for java_file in methods_data:
            print(counter)
            # if counter > 10:
            #     break
            java_file_path = java_file['java_file_path']
            java_file_path = java_file_path.replace('/Users/huhan/PycharmProjects/AndroidSDKParse/sdk_parser/', '')
            print(java_file_path)
            methods_info = java_file['methods_info']
            if methods_info:
                for method_info in methods_info:
                    method_name = method_info['method_name']
                    reply = method_permission_check(str(method_info))
                    if reply:
                        all_data.append({
                            'java_file_path': java_file_path,
                            'method_name': method_name,
                            'permission_prediction': reply
                        })

            counter += 1

    save_path = r'/sdk_parser/permission_prediction.txt'
    with open(save_path, 'w', encoding='utf-8') as f:
        json.dump(all_data, f, indent=4, ensure_ascii=False)


def batch_test2():
    file_path = r'/Users/huhan/PycharmProjects/AndroidSDKParse/sdk_parser/baselines/sdk24/SDK24.json'
    save_path = r'/Users/huhan/PycharmProjects/AndroidSDKParse/sdk_parser/baselines/sdk24/24_permission_prediction.txt'
    checkpoint_path = r'/Users/huhan/PycharmProjects/AndroidSDKParse/sdk_parser/baselines/sdk24/24_checkpoint.txt'

    process_and_write_data(file_path, save_path, checkpoint_path)


def process_and_write_data(file_path, output_path, checkpoint_path):
    # Load data
    with open(file_path, 'r') as file:
        methods_data = json.load(file)
        print(len(methods_data))

    # Determine the starting point if the process restarts
    try:
        with open(checkpoint_path, 'r') as cp_file:
            start_index = int(cp_file.read().strip())
    except FileNotFoundError:
        start_index = 0

    # Open the output file in append mode
    with open(output_path, 'a', encoding='utf-8') as f_out:
        for index, java_file in enumerate(methods_data[start_index:], start=start_index):
            java_file_path = java_file['java_file_path']
            java_file_path = java_file_path.replace('/Users/huhan/PycharmProjects/AndroidSDKParse/sdk_parser/', '')
            print(str(index) + ' ' + java_file_path)
            methods_info = java_file['methods_info']
            if methods_info:
                for method_info in methods_info:
                    method_name = method_info['method_name']
                    reply = method_permission_check(str(method_info))
                    if reply:
                        # Write each result line by line
                        result_line = f"{java_file_path},{method_name},{reply}\n"
                        f_out.write(result_line)

            # Update checkpoint after each file is completely processed
            with open(checkpoint_path, 'w') as cp_file:
                cp_file.write(str(index + 1))


def collect_yes_entries(input_file_path, output_file_path):
    # List to hold lines that meet the criteria
    yes_entries = []

    # Open the input file and process each line
    with open(input_file_path, 'r', encoding='utf-8') as file:
        for line in file:
            # Split the line into parts and check the last part for 'Yes'
            parts = line.strip().split(',')
            if len(parts) > 2 and parts[2].strip() == 'Yes':
                yes_entries.append(line)

    # Optionally, write the yes entries to an output file
    with open(output_file_path, 'w', encoding='utf-8') as outfile:
        for entry in yes_entries:
            outfile.write(entry)

    return yes_entries  # Return the list if needed elsewhere in the program


def parse_llm_results():
    # Define the paths to the input and output files
    input_file_path = r'/Users/huhan/PycharmProjects/AndroidSDKParse/sdk_parser/baselines/sdk24/24_permission_prediction.txt'
    output_file_path = r'/Users/huhan/PycharmProjects/AndroidSDKParse/sdk_parser/baselines/sdk24/24_permission_api.txt'

    # Call the function
    yes_entries = collect_yes_entries(input_file_path, output_file_path)
    print("Collected 'Yes' entries:")
    print("\n".join(yes_entries))


def find_similarity(text1, text2):
    similarity = difflib.SequenceMatcher(None, text1, text2).ratio()
    return similarity


def api_filter_out():
    doc_apis = r'/Users/huhan/PycharmProjects/AndroidSDKParse/document_parser/links.txt'
    permission_prediction = r'/Users/huhan/PycharmProjects/AndroidSDKParse/sdk_parser/permission_prediction.txt'
    save_path = r'/Users/huhan/PycharmProjects/AndroidSDKParse/sdk_parser/permission_prediction_api.txt'
    with open(permission_prediction, 'r', encoding='utf-8') as file2, open(doc_apis, 'r', encoding='utf-8') as file1:
        api_data = file1.readlines()
        permission_data = file2.readlines()
        pairs = []
        for api in api_data:
            api = api.strip().replace('https://developer.android.com/reference/', '')
            for line in permission_data:
                permission = line.split(',')[0]
                permission = permission.strip().replace('android-sdk-sources-for-api-level-35-master/', '')
                permission = permission.replace('.java', '')
                similarity = find_similarity(api, permission)
                if similarity > 0.95:
                    print(similarity)
                    print(api + '---' + permission)
                    if line not in pairs:
                        pairs.append(line)

    with open(save_path, 'w', encoding='utf-8') as file3:
        for pair in pairs:
            file3.write(pair)


def check():
    file = r'/Users/huhan/PycharmProjects/AndroidSDKParse/sdk_parser/permission_prediction_api.txt'
    yes_entries = []
    with open(file, 'r', encoding='utf-8') as file:
        api_data = file.readlines()
        for api in api_data:
            condition = api.split(',')[2]
            if condition == 'Yes':
                yes_entries.append(api)

    file2 = r'/Users/huhan/PycharmProjects/AndroidSDKParse/sdk_parser/permission_prediction_yes_api.txt'
    with open(file2, 'w', encoding='utf-8') as file3:
        for api in yes_entries:
            file3.write(api)


def API_code_generation(API, cls, method):
    model_id = "gpt-4"
    prompt_text1 = r"generate a self-contained method test_" + cls + "_" + method
    prompt_text2 = r" which is an usage example of an android or JAVA API " + API
    prompt_text3 = r'. The method will be used in an android activity.'
    prompt_text4 = r". Just return the code of method test_" + cls + "_" + method
    prompt = prompt_text1 + prompt_text2 + prompt_text3 + prompt_text4
    reply = contact_chatGPT(model_id, prompt)
    return reply


def batch_API_code_generation():
    file1_path = r'/Users/huhan/PycharmProjects/AndroidSDKParse/sdk_parser/permission_prediction_yes_api.txt'
    track_path = r'/Users/huhan/PycharmProjects/AndroidSDKParse/sdk_parser/last_index.txt'  # Path to save the last index
    directory = r'/Users/huhan/PycharmProjects/AndroidSDKParse/sdk_parser/llm_reply/'
    if not os.path.exists(directory):
        os.makedirs(directory)

    # Function to read the last processed index from a file
    def read_last_index():
        try:
            with open(track_path, 'r', encoding='utf-8') as file:
                return int(file.read().strip())
        except FileNotFoundError:
            return 0

    # Function to update the last processed index
    def update_last_index(index):
        with open(track_path, 'w', encoding='utf-8') as file:
            file.write(str(index))

    last_index = read_last_index()  # Get the last processed index

    with open(file1_path, 'r', encoding='utf-8') as file1:
        api_data = file1.readlines()

        # Start from the last processed index + 1 to continue where it left off
        for index, line in enumerate(api_data[last_index:], start=last_index):
            line = line.split(',')
            if len(line) >= 2:
                api = line[0]
                method = line[1].strip()  # Ensure method name is trimmed of whitespace
                api = api.strip().replace('android-sdk-sources-for-api-level-35-master/', '')
                parts = api.split('/')
                class_name = parts[-1].replace('.java', '')  # Get the last part and remove the '.java' extension

                file_name = f'test_{class_name}_{method}.txt'
                saved_file = directory + file_name
                if os.path.exists(saved_file):
                    update_last_index(index)
                    continue

                print('found ' + saved_file)

                reply = API_code_generation(api, class_name, method)
                print(f'Index {index}')

                # Store the content of reply in a txt file named after class_name and method

                with open(saved_file, 'w', encoding='utf-8') as output_file:
                    output_file.write(reply)

                update_last_index(index)  # Update the last index after successful processing


def API_code_generation_r2(content, cls, method):
    model_id = "gpt-4-turbo-2024-04-09"
    prompt_text1 = r"Extract the whole java code of method " + method + ", "
    prompt_text2 = r"and wrap it in a class named " + cls + ". "
    prompt_text3 = r'Only return the code. Do not return any explanations.'
    prompt = prompt_text1 + prompt_text2 + prompt_text3 + '\n\n\n' + content
    reply = contact_chatGPT(model_id, prompt)
    java_code_blocks, non_java_blocks = extract_java_code(reply)
    if len(java_code_blocks) > 1:
        print('multiple code blocks. ' + method)
    return java_code_blocks[0]


def extract_java_code(response_text):
    # Regex to find all code blocks
    code_blocks = re.findall(r"```(?:java|Java)\n(.*?)\n```", response_text, re.DOTALL)

    # Check if extracted code blocks contain Java-specific syntax
    java_code_blocks = []
    non_java_blocks = False

    for block in code_blocks:
        # Basic check for Java syntax, e.g., presence of 'public class', 'import', etc.
        if "public class" in block or "import " in block:
            java_code_blocks.append(block)
        else:
            non_java_blocks = True

    return java_code_blocks, non_java_blocks


def batch_API_code_generation_r2():
    directory = r'/Users/huhan/PycharmProjects/AndroidSDKParse/sdk_parser/llm_reply'
    saved_dir = r'/Users/huhan/PycharmProjects/AndroidSDKParse/sdk_parser/llm_reply_java/'
    checkpoint_file = r'/Users/huhan/PycharmProjects/AndroidSDKParse/sdk_parser/last_processed_r2.txt'

    last_processed = read_last_processed(checkpoint_file)

    print("Checkpoint: Starting batch API code generation...")

    files = os.listdir(directory)
    files.sort()  # Ensure the files are processed in alphabetical order

    for filename in files:
        if filename.endswith(".txt") and filename > last_processed:
            method_name = os.path.splitext(filename)[0]
            class_name = method_name[5:]

            file_path = os.path.join(directory, filename)
            saved_file = os.path.join(saved_dir, class_name + '.java')

            print(f"Checkpoint: Processing {filename}")

            with open(file_path, 'r', encoding='utf-8') as file:
                file_contents = file.read()

            reply = API_code_generation_r2(file_contents, class_name, method_name)

            with open(saved_file, 'w', encoding='utf-8') as save:
                save.write(reply)

            update_last_processed(checkpoint_file, filename)

            print(f"Checkpoint: Saved {class_name}.java")

    print("Checkpoint: Completed all tasks.")


def read_last_processed(filepath):
    try:
        with open(filepath, 'r', encoding='utf-8') as f:
            return f.read().strip()
    except FileNotFoundError:
        return ""


def update_last_processed(filepath, last_file):
    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(last_file)


def check_same_api():

    def unique_line_fields_and_save(filepath, output_filepath):
        seen = set()  # To store unique (first, second) field tuples
        unique_lines = []  # To store the corresponding unique lines

        with open(filepath, 'r') as file:
            for line in file:
                parts = line.strip().split(',')
                if len(parts) < 2:
                    continue  # Skip lines that do not have at least two fields
                key = (parts[0], parts[1])  # Create a tuple of the first and second field
                if key not in seen:
                    seen.add(key)  # Add the tuple to the set if it's not already present
                    unique_lines.append(line.strip())  # Add the whole line to the list

        # Save unique lines to a new file
        with open(output_filepath, 'w') as output_file:
            for line in unique_lines:
                output_file.write(line + '\n')  # Write each line to the output file

        return unique_lines, len(unique_lines)

    # Example usage:
    input_filepath = r'/Users/huhan/PycharmProjects/AndroidSDKParse/sdk_parser/permission_api.txt'

    output_filepath = r'/Users/huhan/PycharmProjects/AndroidSDKParse/sdk_parser/unique_permission_api.txt'
    result, count = unique_line_fields_and_save(input_filepath, output_filepath)
    print("Unique lines based on the first two fields have been saved to:", output_filepath)
    print("Total number of unique lines:", count)



def compareBaselinse():
    filepath = r'/Users/huhan/PycharmProjects/AndroidSDKParse/sdk_parser/baselines/sdk24/24_permission_api.txt'
    seen = []
    with open(filepath, 'r') as file:
        for line in file:
            parts = line.strip().split(',')
            if len(parts) < 2:
                continue  # Skip lines that do not have at least two fields
            filename = os.path.basename(parts[0])
            filename = filename.replace('.java', '')
            key = (filename, parts[1])
            # Create a tuple of the first and second field
            if key not in seen:
                seen.append(key)

    filepath2 = r'/Users/huhan/PycharmProjects/AndroidSDKParse/sdk_parser/baselines/AOSP_7.txt'
    #filepath2 = r'/Users/huhan/PycharmProjects/AndroidSDKParse/sdk_parser/baselines/API_29.txt'
    results = []
    count = 0
    with open(filepath2, 'r') as file2:
        for line in file2:
            if line != '' and line != '\n' and line.startswith('Lcom'):
            #if line != '' and line != '\n':
                for i in seen:
                    if i[1] in line:
                        results.append(i)
                        break

    print(len(results))
    # print(results)


if __name__ == '__main__':
    # unit_test()
    # batch_test2()
    # parse_llm_results()
    # api_filter_out()
    # check()
    # batch_API_code_generation_r2()
    # check_same_api()
    compareBaselinse()
