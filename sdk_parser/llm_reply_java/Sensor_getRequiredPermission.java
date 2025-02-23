public class Sensor_getRequiredPermission {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void test_Sensor_getRequiredPermission() {
        // Get the Sensor Service from the System Service
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager != null) {
            // Get the first available Sensor
            Sensor sensor = sensorManager.getSensorList(Sensor.TYPE_ALL).get(0);

            // Let's assume that our Sensor has a getRequiredPermission method
            String requiredPermission = getRequiredPermission(sensor);

            if (requiredPermission != null) {
                Log.i("SensorPermission", "The sensor requires following permission: " + requiredPermission);
                PackageManager pm = getPackageManager();
                int hasPerm = pm.checkPermission(
                    requiredPermission,
                    getPackageName()
                );
                if (hasPerm != PackageManager.PERMISSION_GRANTED) {
                    Log.i("SensorPermission",
                        "The required permission is not granted. Please check your app permissions.");
                } else {
                    Log.i("SensorPermission", "The required permission is granted.");
                }
            } else {
                Log.i("SensorPermission", "The sensor does not require any specific permissions.");
            }
        }
    }

    /**
     * Let's assume that our Sensor class has a getRequiredPermission method
     */
    private String getRequiredPermission(Sensor sensor) {
        // We just return null for simplicity. You should replace this with actual code.
        return null;
    }
}