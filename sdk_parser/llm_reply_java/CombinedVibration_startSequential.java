import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;

public class CombinedVibration_startSequential {

    private static final int VIBRATOR_PERMISSION_REQ_CODE = 200;

    public void test_CombinedVibration_startSequential() {
        // Check if the VIBRATE permission is already available.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.VIBRATE)
                != PackageManager.PERMISSION_GRANTED) {
            // VIBRATE permission has not been granted.
            requestVibratorPermission();
        } else {
            // VIBRATE permissions is already available, show the camera preview.
            createVibrator();
        }
    }

    private void requestVibratorPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.VIBRATE)) {
                // Provide an additional rationale to the user if the permission was not granted
                // and the user would benefit from additional context for the use of the permission.
                // For example if the user has previously denied the permission.

                // In this particular context, no additional explanation is needed, hence we directly ask for the permission
                ActivityCompat.requestPermissions(this, 
                    new String[]{Manifest.permission.VIBRATE},
                    VIBRATOR_PERMISSION_REQ_CODE);
        } else {
            // directly request for VIBRATE permission.
            ActivityCompat.requestPermissions(this, 
                new String[]{Manifest.permission.VIBRATE},
                VIBRATOR_PERMISSION_REQ_CODE);
        }
    }

    private void createVibrator() {
        // Get instance of Vibrator
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Define your vibration pattern
        long[] pattern = {0, 500, 1000, 500, 1000};
        // Create the vibration effect
        // first parameter in createWaveform is your pattern
        // second parameter is the repeat index, -1 for no repeat
        VibrationEffect vibrationEffect = VibrationEffect.createWaveform(pattern, -1);
        // Vibrate
        vibrator.vibrate(vibrationEffect);
    }
}