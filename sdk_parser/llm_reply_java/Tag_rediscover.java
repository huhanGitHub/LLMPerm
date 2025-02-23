import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.widget.Toast;
import java.io.IOException;

public class Tag_rediscover extends Activity {
    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        mPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, this.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        test_Tag_rediscover();
    }

    public void test_Tag_rediscover() {
        if (mNfcAdapter != null && mPendingIntent != null) {
            try {
                Tag myTag = mNfcAdapter.getPresentTag();

                if (myTag != null) {
                    myTag = mNfcAdapter.rediscover(myTag);

                    if (myTag != null) {
                        Toast.makeText(getApplicationContext(), "NFC Tag Re-discovered!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed to Re-discover the NFC Tag", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No NFC Tag Present", Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}