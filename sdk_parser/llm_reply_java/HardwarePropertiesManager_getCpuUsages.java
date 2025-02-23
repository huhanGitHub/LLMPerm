import android.content.Context;
import android.hardware.HardwarePropertiesManager;
import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;

public class HardwarePropertiesManager_getCpuUsages {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void test_HardwarePropertiesManager_getCpuUsages() {
        // Get an instance of the HardwarePropertiesManager
        HardwarePropertiesManager hardwarePropertiesManager = (HardwarePropertiesManager)
                getSystemService(Context.HARDWARE_PROPERTIES_SERVICE);

        try {
            // Use the HardwarePropertiesManager API to get the CPU's current usage
            float[] cpuUsages = hardwarePropertiesManager.getDeviceTemperatures(
                    HardwarePropertiesManager.DEVICE_TEMPERATURE_CPU,
                    HardwarePropertiesManager.TEMPERATURE_CURRENT);
            
            // Iterate over the CPU usages
            for (int i = 0; i < cpuUsages.length; i++) {
                // Print CPU usage
                Log.d("CPU_USAGE", "CPU #" + i + ": " + cpuUsages[i]);
            }
            
        } catch (Exception e) {
            // Log if something went wrong
            Log.e("HARDWARE_PROPERTIES_ERROR", "Getting CPU usage failed", e);
        }
    }
}