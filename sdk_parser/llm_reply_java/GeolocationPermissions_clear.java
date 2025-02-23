public class GeolocationPermissions_clear {
    public void test_GeolocationPermissions_clear() {
        WebView webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                // Allow geolocation access
                callback.invoke(origin, true, false);
            }
        });

        webView.loadUrl("https://example.com");

        // Wait for the geolocation prompt to be shown and processed
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GeolocationPermissions.getInstance().clear("https://example.com");

        // Check if the permission has been effectively cleared
        GeolocationPermissions.getInstance().allow("https://example.com", new GeolocationPermissions.Callback() {
            @Override
            public void invoke(String origin, boolean allow, boolean retain) {
                if (allow) {
                    throw new AssertionError("Permission was not cleared");
                }
            }
        });
    }
}