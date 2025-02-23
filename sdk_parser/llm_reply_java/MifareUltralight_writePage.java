import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import android.widget.Toast;
import java.io.IOException;

public class MifareUltralight_writePage {

    public void test_MifareUltralight_writePage(Tag tag, int page, byte[] data) {
        MifareUltralight mifare = MifareUltralight.get(tag);
        try {
            mifare.connect();
            mifare.writePage(page, data);
            Toast.makeText(this, "Data written to page " + page, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Write operation has failed", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } finally {
            if (mifare != null) {
                try {
                    mifare.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}