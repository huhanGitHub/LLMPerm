import requests
from bs4 import BeautifulSoup, NavigableString
import re


def clean_text(text):
    # Replace multiple whitespace characters with a single space
    return re.sub(r'\s+', ' ', text).strip().replace('\n', ' ')


def fetch_and_parse_url(url):
    # Send a GET request to the webpage
    response = requests.get(url)
    response.raise_for_status()  # This will raise an exception if the request failed

    # Parse the HTML content of the page with BeautifulSoup
    soup = BeautifulSoup(response.text, 'html.parser')
    return soup

def extract_links(soup):
    # Find all <td> elements with class 'jd-linkcol' and extract the <a> tags within
    tds = soup.find_all('td', class_='jd-linkcol')
    links = [td.find('a')['href'] for td in tds if td.find('a')]
    return links

def save_links_to_file(links, filename='links_AndroidX.txt'):
    # Save the links to a file
    with open(filename, 'w') as file:
        for link in links:
            file.write('https://developer.android.com' + link + '\n')


def extract_permission_info(soup):
    #fast check /reference/androidx/annotation/RequiresPermission
    if '/reference/androidx/annotation/RequiresPermission' not in str(soup):
        return []

    # Find all <code> elements
    code_elements = soup.find_all('code', attrs={'translate': 'no', 'dir': 'ltr'})
    results = []

    for code in code_elements:
        # Check if there's a 'RequiresPermission' in any <a> tag
        permission_links = code.find_all('a', href="/reference/androidx/annotation/RequiresPermission")
        if permission_links:
            permissions = []
            for permission_tag in permission_links:
                sibling = permission_tag.next_sibling
                permission_value = 'yes'
                while sibling:
                    if isinstance(sibling, NavigableString) and "value" in sibling:
                        # Parse the permission value from the string
                        # Example format: (value&nbsp;=&nbsp;"android.permission.ACCESS_FINE_LOCATION")
                        permission_value = sibling.split('"')[1].strip()  # This assumes the value is always in quotes
                        break
                    sibling = sibling.next_sibling
                permissions.append(permission_value)
                # Find the next href with '#'
                for a in code.find_all('a'):
                    link = a.get('href')
                    if '#' in link:
                        # Format the output as specified: link1@text1
                        link_text = a.get_text(strip=True)
                        results.append((permissions, link_text))
                        break  # Only need the first match that fits the criteria

    return results


def extract_permissions():
    with open('links.txt', 'r') as file:
        links = file.readlines()
        for count, url in enumerate(links):
            if count < 2919:
                continue
            print(count)
            # url = 'https://developer.android.com/reference/androidx/core/location/LocationManagerCompat'
            url = url.replace('\n', '')
            soup = fetch_and_parse_url(url)
            results = extract_permission_info(soup)
            print(results)

            with open('permission_pairs.txt', 'a') as file2:
                file2.write(url + '\n')
                for result in results:
                    file2.write(','.join(result[0]) + '---' + result[1] + '\n')


def get_API_class_links():
    # URL of the webpage you want to scrape
    # url = 'https://developer.android.com/reference/classes'
    url = 'https://developer.android.com/reference/androidx/classes'
    soup = fetch_and_parse_url(url)
    links = extract_links(soup)
    save_links_to_file(links)
    print(f'Successfully saved {len(links)} links to links.txt')



def parseManifest():
    url = r'https://developer.android.com/reference/android/Manifest.permission'
    results = []
    response = requests.get(url)
    if response.status_code == 200:
        # Parse the content of the webpage
        soup = BeautifulSoup(response.content, 'html.parser')

        # Find all div elements with a 'data-version-added' attribute
        divs = soup.find_all('div', attrs={'data-version-added': True})
        print(len(divs))
        line = ''
        # Iterate over each div and extract the id from <h3 class="api-name">
        for div in divs[1:]:
            h3_tag = div.find('h3', class_='api-name')
            api_name = 'api_name'
            if h3_tag and h3_tag.has_attr('id'):
                api_name = h3_tag['id']
            print(api_name)
            line += api_name + '---'
            # Extract all <a> tags with a specific href
            a_tags = div.find_all('a', href="/guide/topics/manifest/uses-sdk-element#ApiLevels")

            for a_tag in a_tags:
                api = clean_text(a_tag.text)
                print(api)  # Prints the text within <a>, e.g., "API level 31"
                line += api + '#'

            line += '---'

            # Extract all <code> blocks that contain an <a> tag
            code_blocks = div.find_all('code', translate="no", dir="ltr")
            if not code_blocks:
                print('not find in ' +api_name)
            for code in code_blocks:
                a_tag = code.find('a')  # Find <a> within <code>
                if a_tag:
                    print(a_tag.text)  # Prints the text within <a> inside <code>
                    line += a_tag.text + '---'

            results.append(line)
            line = ''
    else:
        print("Failed to retrieve the webpage.")

    if results:
        with open('permission_pairs.txt', 'w') as file:
            for result in results:
                file.write(result + '\n')


def handle_pairs():
    pairs = r'permission_pairs.txt'
    with open(pairs, 'r') as file:
        lines = file.readlines()
        for line in lines:
            line = line.replace('\n', '')
            fields = line.split('---')
            if len(fields) > 2 and fields[2] != '':
                print(line)




if __name__ == '__main__':
    # get_API_class_links()
    # extract_permissions()
    # parseManifest()
    handle_pairs()
