public class WebChromeClient_onGeolocationPermissionsShowPrompt {

    @Test
    public void test_WebChromeClient_onGeolocationPermissionsShowPrompt() {
        WebView webView = new WebView(context);
        final String[] geoLocations = new String[] {"_test_geolocation"};
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
                
                // Always grant permission assuming you have asked for GEO permissions in AndroidManifest.xml
                // Or ask user permission to access geolocation data
                
                if (shouldGrantPermissionBasedOnYourCondition()) {
                    callback.invoke(origin, true, false);
                } else {
                    callback.invoke(origin, false, false);
                }

               assertEquals(geoLocations[0], origin);
            }
        });

        // Load any url to invoke the prompt
        webView.loadUrl("https://www.example.com");
    }

    private boolean shouldGrantPermissionBasedOnYourCondition() {
        // your condition
        return true;
    }
}