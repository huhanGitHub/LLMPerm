import android.content.Context;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.OrientationEventListener;

public class OrientationEventListener_enable {

    public void test_OrientationEventListener_enable() {
        Context context = this;  // or getApplicationContext(), dependent on your need
        int rate = SensorManager.SENSOR_DELAY_NORMAL;
        OrientationEventListener listener = new OrientationEventListener(context, rate) {
            @Override
            public void onOrientationChanged(int orientation) {
                // handle here
                if (orientation == ORIENTATION_UNKNOWN) {
                    // sensor turned off
                    Log.d("OrientationEventListener", "Sensor turned off");
                } else {
                    // sensor turned on, process orientation value
                    Log.d("OrientationEventListener", "Orientation: " + orientation);
                }
            }
        };

        if (listener.canDetectOrientation()) {
            listener.enable();
            Log.d("OrientationEventListener", "Listener enabled");
        } else {
            Log.d("OrientationEventListener", "Cannot detect orientation");
            listener.disable();
        }
    }
}