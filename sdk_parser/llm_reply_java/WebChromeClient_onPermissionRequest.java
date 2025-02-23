import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class WebChromeClient_onPermissionRequest {
    public void test_WebChromeClient_onPermissionRequest() {
        WebView myWebView = new WebView(null);

        myWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                myWebView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (request.getOrigin().toString().equals("https://my.trusted.site.com")) {
                            request.grant(request.getResources());
                        } else {
                            request.deny();
                        }
                    }
                });

            }

            @Override
            public void onPermissionRequestCanceled(PermissionRequest request) {
                super.onPermissionRequestCanceled(request);
            }
        });
    }
}