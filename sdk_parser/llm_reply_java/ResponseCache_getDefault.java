import java.net.ResponseCache;
import android.util.Log;

public class ResponseCache_getDefault {
    public void test_ResponseCache_getDefault() {
        // Get the system-wide response cache
        ResponseCache responseCache = ResponseCache.getDefault();
        
        if (responseCache != null) {
            // Print the class name of the response cache
            Log.d("ResponseCache getDefault Example", "Default ResponseCache implementation is: " + responseCache.getClass().getName());
        } else {
            // If 'responseCache' is null, Android doesn't support the default implementation.
            Log.d("ResponseCache getDefault Example", "No default ResponseCache implementation is found.");
        }
    }
}