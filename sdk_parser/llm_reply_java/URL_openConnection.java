public class URL_openConnection {

    private void test_URL_openConnection(String urlString) {
        try {
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();

            // Log.d(TAG, "Connection successful to URL: " + urlString);

            // read and display the response body
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            // Log.d(TAG, "Response: " + result.toString());

        } catch (MalformedURLException e) {
            // Log.e(TAG, "Invalid URL", e);
        } catch (IOException e) {
            // Log.e(TAG, "Error opening connection", e);
        }
    }
}