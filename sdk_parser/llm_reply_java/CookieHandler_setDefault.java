public class CookieHandler_setDefault {
    public void test_CookieHandler_setDefault() {
        try {
            CookieManager cookieManager = new CookieManager();
            CookieHandler.setDefault(cookieManager);

            // Now create a connection to check our CookieHandler
            URL url = new URL("http://www.google.com");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // Get cookies from responses and add them
            Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
            List<String> cookiesHeader = headerFields.get("Set-Cookie");

            if (cookiesHeader != null) {
                for (String cookie : cookiesHeader) {
                    cookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                }
            }

            // Now the cookieManager is our default CookieHandler.
            // All subsequent HttpUrlConnections will use this CookieHandler by default.
            CookieHandler defaultCookieHandler = CookieHandler.getDefault();
            if (defaultCookieHandler.equals(cookieManager)) {
                System.out.println("Successful: CookieHandler.setDefault works fine");
            } else {
                System.out.println("Failed: CookieHandler.setDefault doesn't work");
            }

        } catch (IOException e) {
            System.out.println("Error occurred: " + e);
        }
    }
}