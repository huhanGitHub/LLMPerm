public class HapticGenerator_setEnabled {
    public void test_HapticGenerator_setEnabled() {
        try {
            // Enable the Haptic Generator
            hapticGenerator.setEnabled(true);
            
            //Print status
            if(hapticGenerator.getEnabled()) {
                Log.d("HapticGenerator", "HapticGenerator is enabled");
            } else {
                Log.d("HapticGenerator", "Failed to enable the HapticGenerator");
            }    
            
        } catch (Exception e) {
            Log.e("HapticGenerator", "Error in enabling the HapticGenerator", e);
        }
    }
}