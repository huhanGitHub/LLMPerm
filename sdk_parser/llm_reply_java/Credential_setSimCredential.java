import android.net.wifi.hotspot2.pps.Credential;
import android.net.wifi.hotspot2.pps.Credential.SimCredential;
import android.util.Log;

public class Credential_setSimCredential {
    public void test_Credential_setSimCredential() {
        Credential credential = new Credential();
        Credential.SimCredential simCredential = new Credential.SimCredential();

        // Set EAP type
        simCredential.setEapType(18);
        // Set IMSI
        simCredential.setImsi("12345*");

        try {
            credential.setSimCredential(simCredential);

            Log.d("test", "Credential sim credential set successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}