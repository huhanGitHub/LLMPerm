public class ScanSettings_setScanMode {
    // import necessary packages
    import android.bluetooth.le.ScanSettings;
    import android.os.Build;
    import androidx.annotation.RequiresApi;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void test_ScanSettings_setScanMode() {
        // Create a ScanSettings object
        ScanSettings scanSettings = null;

        // Create a ScanSettings.Builder object
        ScanSettings.Builder builder = new ScanSettings.Builder();

        // Set scan mode
        builder.setScanMode(ScanSettings.SCAN_MODE_LOW_POWER);

        // 
        try {
            // Build scan settings
            scanSettings = builder.build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Check scan mode
        int mode = scanSettings.getScanMode();

        // Print scan mode
        switch(mode){
            case ScanSettings.SCAN_MODE_LOW_POWER:
                System.out.println("Scan mode: Low power");
                break;
            case ScanSettings.SCAN_MODE_BALANCED:
                System.out.println("Scan mode: Balanced");
                break;
            case ScanSettings.SCAN_MODE_LOW_LATENCY:
                System.out.println("Scan mode: Low latency");
                break;
            case ScanSettings.SCAN_MODE_OPPORTUNISTIC:
                System.out.println("Scan mode: Opportunistic");
                break;
            default:
                System.out.println("Scan mode: Unknown"); 
        }
    }
}