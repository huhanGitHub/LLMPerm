import difflib
import re
from wordcloud import WordCloud
import matplotlib.pyplot as plt

def find_similarity(method, line2_method):
    similarities = []
    for elem in line2_method:
        # Calculate the similarity ratio
        similarity = difflib.SequenceMatcher(None, method, elem).ratio()
        similarities.append((elem, similarity))
    return similarities


def process(str):
    str = re.sub(r'\(.*?\)', '', str)
    str = str.split('.')[-1]
    str = str.replace('<', '')
    str = str.replace('>', '')
    return str


def word_cloud(text):
    # Create a word cloud
    wordcloud = WordCloud(width=800, height=400, background_color="white").generate(text)

    # Display the word cloud
    plt.figure(figsize=(10, 5))
    plt.imshow(wordcloud, interpolation="bilinear")
    plt.axis("off")
    plt.show()


def compare():
    file1 = r'permission_pairs_filter.txt'
    file2 = r'NtDroid29.txt'
    count = 0
    words = []

    with open(file1) as f1, open(file2) as f2:
        lines = f1.readlines()
        lines2 = f2.readlines()

        for line in lines:
            line = line.replace('\n', '')
            line = line.split('---')
            pms = line[0]
            api = line[1]
            methods = line[2:]

            match = re.search(r'\d+', api)
            api_version = int(match.group())

            if api_version > 29:
                #print(line)
                continue

            for line2 in lines2:
                line2 = line2.replace('\n', '')
                line2 = line2.split('  ::  ')
                line2_method = line2[0].split()
                line2_method = [process(i) for i in line2_method]
                for method in methods:
                    if method:
                        method = process(method)
                        similarities = find_similarity(method, line2_method)
                        max_similarity = max(similarities, key=lambda x: x[1])
                        if max_similarity[1] > 0.7:
                            count += 1
                            print(method + '---' + str(max_similarity))

    print(count)

def compare_permission():
    file1 = r'permission_pairs_filter.txt'
    file2 = r'NtDroid29.txt'
    words = []
    with open(file1) as f1, open(file2) as f2:
        lines = f1.readlines()
        lines2 = f2.readlines()
        for line in lines:
            line = line.replace('\n', '')
            line = line.split('---')
            pms = line[0]
            print(pms)
            words.extend(pms.split('_'))
            api = line[1]
            methods = line[2:]

            match = re.search(r'\d+', api)
            api_version = int(match.group())

            if api_version > 29:
                #print(line)
                continue

            for line2 in lines2:
                line2 = line2.replace('\n', '')
                if pms in lines2:
                    print(pms + '---' + line2)

    text = ' '.join(words)
    word_cloud(text)


def bar_chart(text):
    from collections import Counter

    # Split the text into words (ignoring case) and remove punctuation
    words = text.lower().replace('.', '').split()

    # Count the frequency of each word
    word_count = Counter(words)

    # Extract words and their frequencies for plotting
    words = list(word_count.keys())
    frequencies = list(word_count.values())

    for word, frequency in zip(words, frequencies):
        print(word, frequency)

    # Create the bar chart
    plt.figure(figsize=(10, 5))
    plt.bar(words, frequencies, color='skyblue')

    # Add labels and title
    plt.xlabel('Words')
    plt.ylabel('Frequency')
    plt.title('Permission Frequency in NatiDroid')

    # Display the bar chart
    plt.xticks(rotation=45)  # Rotate the words on the x-axis for better readability
    plt.tight_layout()  # Adjust layout to fit labels
    plt.show()


def find():
    file2 = r'NtDroid29.txt'
    rets = []
    with open(file2) as f2:
        lines = f2.readlines()
        for line in lines:
            # Regex to match any 'android.permission.<ANYTHING>' pattern
            pattern = r'android\.permission\.\w+'

            # Find all matches
            matches = re.findall(pattern, line)

            # Display the results
            if matches:
                print(matches)
                matches = [i.replace('android.permission.', '') for i in matches]
                rets.extend(matches)


    rets = ' '.join(rets)
    bar_chart(rets)





if __name__ == '__main__':
    # compare_permission()
    compare_permission()
    # find()