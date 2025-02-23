import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.nfc.tech.TagTechnology;
import android.util.Log;
import java.io.IOException;

public class TagTechnology_connect {
    public void test_TagTechnology_connect() {
        try {
            Tag tag = ...; // we must fetch the tag, usually it is from an Intent in the onNewIntent method of your Activity
            if (tag != null) {
                TagTechnology tagTech = NfcA.get(tag);
                if (!tagTech.isConnected()) {
                    tagTech.connect();
                } // You are now connected to the tag and can perform I/O operations
                // End your work by closing the connection
                if (tagTech.isConnected()) {
                    tagTech.close();
                }
            }
        } catch (IOException ioe) {
            Log.e("TAG", ioe.getMessage());
            // Handle IOException. This exception is thrown when I/O operation failed or your phone does not support NFC
        }
    }
}