public class CombinedVibration_createParallel {
    public void test_CombinedVibration_createParallel() {
        // Create multiple vibration effects.
        VibrationEffect effect1 = VibrationEffect.createOneShot(1000L, VibrationEffect.DEFAULT_AMPLITUDE);
        VibrationEffect effect2 = VibrationEffect.createOneShot(2000L, VibrationEffect.DEFAULT_AMPLITUDE);
        VibrationEffect effect3 = VibrationEffect.createOneShot(3000L, VibrationEffect.DEFAULT_AMPLITUDE);

        // Combine them into an array.
        VibrationEffect[] effects = new VibrationEffect[]{effect1, effect2, effect3};

        // Call createParallel() to create a new CombinedVibration instance that plays the effects together.
        CombinedVibration combinedVibration = CombinedVibration.createParallel(effects, new int[]{0,0,0});

        // Then you would typically use this combined vibration with a Vibration service.
        // Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        // vibrator.vibrate(combinedVibration);
    }
}