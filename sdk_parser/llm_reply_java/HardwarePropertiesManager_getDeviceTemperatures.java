import android.content.Context;
import android.os.HardwarePropertiesManager;

public class HardwarePropertiesManager_getDeviceTemperatures {
    public void test_HardwarePropertiesManager_getDeviceTemperatures(Context context) {
        HardwarePropertiesManager hardwarePropertiesManager =
                (HardwarePropertiesManager) context.getSystemService(Context.HARDWARE_PROPERTIES_SERVICE);
        
        if (hardwarePropertiesManager != null) {
            float[] deviceTemperatures;
            
            // Get CPU temperatures
            deviceTemperatures = hardwarePropertiesManager.getDeviceTemperatures(
                    HardwarePropertiesManager.DEVICE_TEMPERATURE_CPU,
                    HardwarePropertiesManager.TEMPERATURE_CURRENT);
            for (float temp : deviceTemperatures) {
                System.out.println("CPU Temperature: " + temp);
            }
            
            // Get GPU temperatures
            deviceTemperatures = hardwarePropertiesManager.getDeviceTemperatures(
                    HardwarePropertiesManager.DEVICE_TEMPERATURE_GPU,
                    HardwarePropertiesManager.TEMPERATURE_CURRENT);
            for (float temp : deviceTemperatures) {
                System.out.println("GPU Temperature: " + temp);
            }
            
            // Get Battery temperatures
            deviceTemperatures = hardwarePropertiesManager.getDeviceTemperatures(
                    HardwarePropertiesManager.DEVICE_TEMPERATURE_BATTERY,
                    HardwarePropertiesManager.TEMPERATURE_CURRENT);
            for (float temp : deviceTemperatures) {
                System.out.println("Battery Temperature: " + temp);
            }
        }
    }
}