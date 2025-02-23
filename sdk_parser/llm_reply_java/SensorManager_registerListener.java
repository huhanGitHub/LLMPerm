public class SensorManager_registerListener {

    public void test_SensorManager_registerListener(SensorManager sensorManager, Sensor sensor, SensorEventListener listener) {
        // Register the listener for this sensor
        if(sensorManager != null && sensor != null)
            sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
}