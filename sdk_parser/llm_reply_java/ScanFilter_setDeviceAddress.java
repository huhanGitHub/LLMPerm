import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;
import android.bluetooth.le.ScanFilter;

public class ScanFilter_setDeviceAddress {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void test_ScanFilter_setDeviceAddress() {
        try {
            // An example MAC address. This might come from a real device in your environment.
            final String testDeviceAddress = "00:11:22:33:44:55";

            // Create a ScanFilter builder.
            ScanFilter.Builder scanFilterBuilder = new ScanFilter.Builder();

            // Set the device address of scan filter.
            scanFilterBuilder.setDeviceAddress(testDeviceAddress);

            // Build the ScanFilter
            ScanFilter scanFilter = scanFilterBuilder.build();

            // Get the device address of ScanFilter.
            String deviceAddress = scanFilter.getDeviceAddress();

            // Check if the input MAC address matches the builder-set MAC address.
            if (!testDeviceAddress.equals(deviceAddress)) {
                throw new Exception("ScanFilter setDeviceAddress failed: expected " + testDeviceAddress + " but got " + deviceAddress);
            }

            // Log the success message 
            Log.d("test_ScanFilter_setDeviceAddress", "ScanFilter setDeviceAddress passed.");
        } catch (Exception ex) {
            // Log the error message
            Log.e("test_ScanFilter_setDeviceAddress", "Error in test_ScanFilter_setDeviceAddress: " + ex.getMessage());
        }
    }
}