public class Sensor_getMinDelay {

    public void test_Sensor_getMinDelay() {
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelerometerSensor != null) {
            int minDelay = accelerometerSensor.getMinDelay();
            Log.d("Sensor_getMinDelay", "Min Delay for Accelerometer Sensor: " + minDelay);
        } else {
            Log.d("Sensor_getMinDelay", "No Accelerometer Sensor found");
        }
    }
}