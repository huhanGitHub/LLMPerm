import android.content.Context;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.widget.Toast;

public class NfcF_getTimeout {
    public void test_NfcF_getTimeout(Context context) {
        // This sample tag instance represents an NFC-F type tag you might find on the field
        Tag sampleTag = Tag.CREATOR.createFromParcel(null);

        NfcF nfcF = NfcF.get(sampleTag);

        if(nfcF != null) {
            try {
                // Connect to the NFC tag
                nfcF.connect();

                // Get the timeout of the NfcF tag
                int timeout = nfcF.getTimeout();

                // Print out the timeout to user
                Toast.makeText(context, "NFC-F Tag Timeout: " + timeout + "ms", Toast.LENGTH_SHORT).show();

                // Always remember to disconnect when done working with the NFC tag
                nfcF.close();
            } catch (Exception e) {
                Toast.makeText(context, "Error reading timeout: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "The tag is not an NFC-F tag.", Toast.LENGTH_SHORT).show();
        }
    }
}