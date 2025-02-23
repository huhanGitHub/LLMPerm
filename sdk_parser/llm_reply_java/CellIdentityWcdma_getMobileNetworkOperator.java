import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfoWcdma;
import android.telephony.wcdma.WcdmaCellLocation;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;

public class CellIdentityWcdma_getMobileNetworkOperator {

    private static final int REQUEST_READ_PHONE_STATE = 0;

    public void test_CellIdentityWcdma_getMobileNetworkOperator() {
        // Check if the READ_PHONE_STATE permission is already available.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // READ_PHONE_STATE permission has not been granted.
            requestReadPhoneStatePermission();
        } else {
            // READ_PHONE_STATE permission is already available, we can get network operator directly.
            getMobileNetworkOperator();
        }
    }

    private void getMobileNetworkOperator() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        // getNetworkOperatorName() can be used directly if the API level is less than 28.
        // But for API level 28 and above, you should use CellIdentityWcdma to get more specific information.
        String networkOperator = telephonyManager.getNetworkOperatorName();

        if (telephonyManager.getAllCellInfo().get(0) instanceof CellInfoWcdma) {
            CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) telephonyManager.getAllCellInfo().get(0);
            CellIdentityWcdma wcdmaIdentity = cellInfoWcdma.getCellIdentity();
            networkOperator = "MCC: " + wcdmaIdentity.getMccString() + " MNC: " + wcdmaIdentity.getMncString();
        }

        System.out.println("Network Operator is: " + networkOperator);
    }

    private void requestReadPhoneStatePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
            // Provide an additional rationale to the user if the permission was not granted and the user would benefit from additional context for the use of the permission.
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        } else {
            // READ_PHONE_STATE permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_READ_PHONE_STATE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // READ_PHONE_STATE permission has been granted, we can get network operator
                getMobileNetworkOperator();
            } else {
                // READ_PHONE_STATE permission was NOT granted
                // Show an explanation to the user and try to request the permission again at a later time.
            }
        }
    }
}