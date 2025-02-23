import javalang

# Java source code as a string
java_code = """
public class Test {
    public void fetchData() {
        HttpURLConnection conn = (HttpURLConnection) new URL("http://example.com").openConnection();
        conn.setRequestMethod("GET");
        InputStream is = conn.getInputStream();
    }
}
"""

# Parse the source code
tree = javalang.parse.parse(java_code)


# Function to recursively find method calls
def find_api_calls(node, api_calls):
    if isinstance(node, (javalang.tree.MethodInvocation, javalang.tree.ClassCreator)):
        # Add method calls and constructor calls (like new URL(...))
        api_calls.append(node.member if hasattr(node, 'member') else node.type.name)

    # Only try to access children if node is a Node instance that could have children
    if hasattr(node, 'children') and node.children:
        for child in node.children:
            find_api_calls(child, api_calls)


# Extract API calls from the fetchData method
api_calls = []
for path, node in tree.filter(javalang.tree.ClassDeclaration):
    for body in node.body:
        if isinstance(body, javalang.tree.MethodDeclaration) and body.name == "fetchData":
            find_api_calls(body, api_calls)

print("API calls in fetchData method:", api_calls)
