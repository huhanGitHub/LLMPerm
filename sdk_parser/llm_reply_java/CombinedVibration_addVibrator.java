public class CombinedVibration_addVibrator {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void test_CombinedVibration_addVibrator() {
        // Check whether the device has a vibrator
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        
        if(vibrator == null || !vibrator.hasVibrator()) {
            System.out.println("This device does not support vibration");
            return;
        }
        
        // Check whether vibrate permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("Vibration permission needed");
            // Ask for the permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.VIBRATE}, 1);
            return;
        }

        // Create a vibration pattern
        long[] pattern = {1000, 500, 1000, 500};
        
        // Send pattern to the Vibrator
        VibrationEffect vibe = VibrationEffect.createWaveform(pattern, VibrationEffect.DEFAULT_AMPLITUDE);
        vibrator.vibrate(vibe);
    }
}