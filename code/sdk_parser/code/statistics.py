from collections import Counter


def count_api_categories(api_data):
    """
    Counts occurrences of API-permission mappings by their package categories.

    Args:
    api_data (list of str): A list where each element is a string describing an API case,
                            including its path and permission requirement description.

    Returns:
    dict: A dictionary with package categories as keys and counts as values.
    """
    # Initialize a Counter to keep track of category counts
    category_counts = Counter()

    # Iterate over each API data entry
    for data in api_data:
        # Split the data by commas and get the first element (path)
        path = data.split(',')[0]
        # Extract the first part of the path as the category (e.g., /android from /android/database/sqlite...)
        category = path.split('/')[1]
        # Update the count for this category
        category_counts[category] += 1

    return dict(category_counts)


# Example data
api_examples = [
    "/org/w3c/domts/BatikTestDocumentBuilderFactory.java,load,Yes, this method requires permissions to access the network to load the specified URL.",
    "/java/net/MulticastSocket.java,leaveGroup,Yes, because it calls `checkMulticast` which may require appropriate permissions to access network features.",
    "/javax/net/ServerSocketFactory.java,createServerSocket,Yes, it requires permissions. It checks the security manager's `checkListen` method to ensure the operation is allowed, which can throw a `SecurityException`.",
    "/android/database/sqlite/SQLiteDatabase.java,updateWithOnConflict,Yes, this method requires permissions to access and modify the database."
]


def test_file():
    file = r'E:\research\smu\smu mac\files\PycharmProjects\AndroidSDKParse\sdk_parser\baselines\sdk35\35_permission_api_35.txt'

    with open(file) as f:
        lines = f.readlines()
        category_counts = count_api_categories(lines)
        print(category_counts)


if __name__ == '__main__':
    test_file()
