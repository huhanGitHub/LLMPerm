public class CombinedVibration_startParallel {

    public void test_CombinedVibration_startParallel(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.VIBRATE}, 1);
        } else {
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            
            if (vibrator != null && vibrator.hasVibrator()) {
                VibrationEffect vibe1 = VibrationEffect.createOneShot(500, VibrationEffect.EFFECT_HEAVY_CLICK);
                VibrationEffect vibe2 = VibrationEffect.createOneShot(500, VibrationEffect.EFFECT_TICK);

                CombinedVibration combined = CombinedVibration.startParallel()
                        .addVibration(0, vibe1)
                        .addVibration(1, vibe2)
                        .combine();

                vibrator.vibrate(combined);
            }
        }
    }
}