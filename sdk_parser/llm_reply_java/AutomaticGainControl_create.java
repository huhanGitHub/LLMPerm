public class AutomaticGainControl_create {
    public void test_AutomaticGainControl_create() {
        final int audioSessionId = 0; // Replace with the audio session ID you need.
        if (AutomaticGainControl.isAvailable()) {
            AutomaticGainControl agc = AutomaticGainControl.create(audioSessionId);
            if (agc != null) {
                boolean enabled = agc.getEnabled();
                int status = agc.setEnabled(!enabled); // toggle it
                if (status == AudioEffect.SUCCESS) {
                    Log.d("AGC_Test", enabled ? "AGC disabled" : "AGC enabled");
                } else {
                    Log.w("AGC_Test", "Failed to change AGC status");
                }
                agc.release(); // release after use
            } else {
                Log.e("AGC_Test", "Failed to create AutomaticGainControl");
            }
        } else {
            Log.w("AGC_Test", "AutomaticGainControl is not available on this device");
        }
    }
}