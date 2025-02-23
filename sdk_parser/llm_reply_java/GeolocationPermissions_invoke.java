import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class GeolocationPermissions_invoke {
    public void test_GeolocationPermissions_invoke() {
        WebView myWebView = new WebView(this);
        myWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, 
                                   GeolocationPermissions.Callback callback) {
                if (origin.equals("https://example.com")) {
                    callback.invoke(origin, true, true);
                } else {
                    callback.invoke(origin, false, false);
                }
            }
        });
        myWebView.loadUrl("https://example.com");
    }
}