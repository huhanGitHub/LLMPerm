import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.util.Log;
import java.io.IOException;

public class IsoDep_getTimeout {
    public void test_IsoDep_getTimeout() {
        Tag tag = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag != null) {
            IsoDep isoDep = IsoDep.get(tag);
            if (isoDep != null) {
                try {
                    isoDep.connect();
                    int timeout = isoDep.getTimeout();
                    Log.d("IsoDep Timeout", "Timeout: " + timeout);
                    isoDep.close();
                } catch (IOException e) {
                    Log.e("IsoDep Error", e.getMessage());
                }
            }
        }
    }
}