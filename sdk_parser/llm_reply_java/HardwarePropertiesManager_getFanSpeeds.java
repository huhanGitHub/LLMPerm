public class HardwarePropertiesManager_getFanSpeeds {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void test_HardwarePropertiesManager_getFanSpeeds() {
        // checking if the current version of Android supports HardwarePropertiesManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

            // getting the system service for HardwarePropertiesManager
            HardwarePropertiesManager hardwarePropertiesManager = (HardwarePropertiesManager) getSystemService(Context.HARDWARE_PROPERTIES_SERVICE);

            try {
                // getting fan speeds using the getFanSpeeds method
                float[] fanSpeeds = hardwarePropertiesManager.getFanSpeeds();

                // it's always good to check if the returned value is not null
                if (fanSpeeds != null) { 
                    for (float fanSpeed: fanSpeeds) {
                        // Printing the fan speed values to the console
                        System.out.println("Fan speed: " + fanSpeed);
                    }
                }

            } catch (Exception e) {
                // catching possible exceptions
                e.printStackTrace();
            }

        } else {

            // Print out a message if the current version of Android does not support HardwarePropertiesManager
            System.out.println("HardwarePropertiesManager is not supported on this device");
        }
    }
}