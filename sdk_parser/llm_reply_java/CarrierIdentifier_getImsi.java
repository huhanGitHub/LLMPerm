import android.telephony.TelephonyManager;
import android.content.Context;
import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

public class CarrierIdentifier_getImsi extends AppCompatActivity {

    public void test_CarrierIdentifier_getImsi() {

        // Get the telephony service
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        // Making sure we have the required permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // If permission not granted, ask for it
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        } else {
            // If permission is granted, retrieve IMSI
            getImsi(telephonyManager);
        }
    }

    public void getImsi(TelephonyManager telephonyManager) {
        try {
            String imsi = telephonyManager.getSubscriberId();

            // display imsi in logs
            Log.d("IMSI", "IMSI: " + imsi);

        } catch (SecurityException e) {
            e.printStackTrace();
            Toast.makeText(this, "Permission denied. Can't read phone state.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            getImsi(telephonyManager);
        }
    }
}