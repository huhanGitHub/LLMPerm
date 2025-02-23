public class URL_checkSpecifyHandler {
    private void test_URL_checkSpecifyHandler() {
        // Define the URL string
        String urlString = "https://www.example.com";

        try {
            // Specify a default URLStreamHandler by creating anonymous class
            URLStreamHandler handler = new URLStreamHandler() {
                @Override
                protected URLConnection openConnection(URL url) throws IOException {
                    // Insert your custom connection settings here
                    return url.openConnection(Proxy.NO_PROXY); 
                }
            };

            // Create URL object using URL string and custom URLStreamHandler
            URL url = new URL(null, urlString, handler);

            // Use URL object as needed
            URLConnection connection = url.openConnection();
            // ...
        } catch (MalformedURLException e) {
            // Handle malformed URL
            e.printStackTrace();
        } catch (IOException e) {
            // Handle IO error
            e.printStackTrace();
        }
    }
}