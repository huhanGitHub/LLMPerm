public class CombinedVibration_addNext {

    import android.os.CombinedVibration;
    import android.os.VibrationEffect;
    import android.os.Vibrator;
    import android.content.Context;
    import android.Manifest;
    import android.content.pm.PackageManager;
    import android.util.Log;

    public void test_CombinedVibration_addNext() {
        Context context = this;

        // Check for vibration permission
        if (context.checkSelfPermission(Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

            if(vibrator != null) {
                if (vibrator.hasVibrator()) {
                    VibrationEffect vibrationEffect1 = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE);
                    VibrationEffect vibrationEffect2 = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE);

                    CombinedVibration combinedVibration = CombinedVibration.startParallel().addNext(vibrationEffect1).addNext(vibrationEffect2).combine();

                    // requires minimum API of 29.
                    vibrator.vibrate(combinedVibration);
                } else {
                    Log.v("Vibrator_Service", "This device does not support vibration");
                }
            } else {
                Log.v("Vibrator_Service", "Vibrator service is not available");
            }
        } else {
            Log.v("Vibrator_Service", "Vibration permission is not granted");
        }
    }

}