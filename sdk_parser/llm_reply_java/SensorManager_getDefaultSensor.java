import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.content.Context;

public class SensorManager_getDefaultSensor extends AppCompatActivity {

    public void test_SensorManager_getDefaultSensor() {
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelerometerSensor != null) {
            System.out.println("The device has an accelerometer sensor");
        } else {
            System.out.println("The device doesn't have an accelerometer sensor");
        }
    }
}