import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.util.Log;

public class TagTechnology_reconnect {

    public void test_TagTechnology_reconnect() throws Exception {
        // creating a mock NFC tag
        Tag tag = Tag.CREATOR.createFromParcel(null);

        // get an instance of NfcA which is a subclass of TagTechnology
        NfcA nfcA = NfcA.get(tag);

        if(nfcA != null){
            // Checking is TagTechnology is already connected
            if(!nfcA.isConnected()) {
                nfcA.connect();
            }

            // Disconnect from the Tag
            nfcA.close();

            // Reconnect to the Tag
            nfcA.connect();
            
            if(nfcA.isConnected()) {
                Log.d("NFC Test", "Reconnection successful");
            } else {
                Log.d("NFC Test", "Reconnection failed");
            }
        } else {
            Log.d("NFC Test", "NfcA instance is null");
        }
    }
}