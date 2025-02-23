import android.telephony.NetworkScan;
import android.telephony.TelephonyManager;
import android.telephony.TelephonyScanManager;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.List;
import android.telephony.CellInfo;

public class NetworkScan_stopScan {
    public void test_NetworkScan_stopScan() {
        // Check if the Application has the necessary permissions
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE}, 123);
                return;
            }
        }
        
        // Get the TelephonyManager from the system service
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        TelephonyScanManager telephonyScanManager = telephonyManager.createScanManager();

        // Define the Scan Callback
        TelephonyScanManager.NetworkScanCallback callback = new TelephonyScanManager.NetworkScanCallback() {
            @Override
            public void onResults(List<CellInfo> results) {
                super.onResults(results);
            }

            @Override
            public void onError(int error) {
                super.onError(error);
            }

            @Override
            public void onUnavailable() {
                super.onUnavailable();
            }
        };

        // Trigger Scan
        NetworkScan networkScan = telephonyScanManager.requestNetworkScan(
                        new NetworkScanRequest(NETWORK_SCAN_TYPE_ONE_TIME_SCAN, getRadioAccessSpecifierList(), MAX_SEARCH_TIME, INCREMENTAL_RESULTS_PERIODICITY, true, true), 
                        getMainExecutor(), 
                        callback);

        // Stop the network scan
        if (networkScan != null) {
            networkScan.stopScan();
        }
    }
}