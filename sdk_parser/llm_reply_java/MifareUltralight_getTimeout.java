import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import android.util.Log;
import java.io.IOException;

public class MifareUltralight_getTimeout {
    public void test_MifareUltralight_getTimeout(){
        try {
            // create a mock tag
            Tag tag = null;

            // get a MIFARE Ultralight instance for the tag
            MifareUltralight mifare = MifareUltralight.get(tag);

            // connect to the card
            mifare.connect();

            // print the timeout value
            int timeoutValue = mifare.getTimeout();
            Log.d("MifareUltralight", "Timeout value: " + timeoutValue);

            // always close your connection!
            mifare.close();

        } catch (IOException e) {
            // connection error
            Log.e("MifareUltralight", "Error while testing MifareUltralight test_MifareUltralight_getTimeout: " + e.toString());
        }
    }
}