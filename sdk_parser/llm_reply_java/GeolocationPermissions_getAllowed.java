public class GeolocationPermissions_getAllowed {
    /**
     * The method queries the GeoLocation permissions for a website origin.
     */
    private void test_GeolocationPermissions_getAllowed() {
        WebView webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                GeolocationPermissions.getInstance().getAllowed(origin, new GeolocationPermissions.Callback() {
                    @Override
                    public void invoke(String origin, boolean allowed, boolean retained) {
                        if (allowed) {
                            Log.v("GeolocationPermissionTest", origin + " is allowed to use geolocation.");
                        } else {
                            Log.v("GeolocationPermissionTest", origin + " is not allowed to use geolocation.");
                        }
                    }
                });
            }
        });

        // Load a webpage that will request geolocation permissions.
        webView.loadUrl("https://www.example.com");
    }
}