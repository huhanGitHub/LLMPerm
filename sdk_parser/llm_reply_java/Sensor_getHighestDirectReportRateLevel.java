import android.hardware.Sensor;
import android.util.Log;

public class Sensor_getHighestDirectReportRateLevel {

    public void test_Sensor_getHighestDirectReportRateLevel(Sensor sensor) {
        if (sensor != null) {
            int highestRateLevel = sensor.getHighestDirectReportRateLevel();

            // Now we perform a check on the returned value.
            if (highestRateLevel >= 0) {
                Log.i("testMethod", "Highest Direct Report Rate Level: " + highestRateLevel);
            } else {
                Log.i("testMethod", "Method not supported");
            }
        } else {
            Log.i("testMethod", "Sensor not available");
        }
    }

}