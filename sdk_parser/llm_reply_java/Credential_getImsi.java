import android.net.wifi.hotspot2.pps.Credential;
import android.os.Build;
import android.util.Log;

public class Credential_getImsi {
    public void test_Credential_getImsi() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            Credential credential = new Credential();

            // You need to set IMSI before you get
            Credential.SimCredential simCredential = new Credential.SimCredential();
            simCredential.setImsi("testImsi12345");
            credential.setSimCredential(simCredential);

            String imsi = credential.getSimCredential().getImsi();
            Log.i("Credential IMSI: ", imsi);
        } else {
            Log.i("Test failed", "This method is deprecated at your API Level");
        }
    }
}