public class NfcFCardEmulation_registerSystemCodeForService {

    private boolean test_NfcFCardEmulation_registerSystemCodeForService() {
        String packageName = "com.example.test";  // Update it with your package name.
        String serviceClassName = "com.example.test.MyNfcFService";  // Update with your NFC service class name.
        String systemCode = "4056";  // Replace it with your system code.
        boolean success = false;

        // Get an instance of NfcFCardEmulation.
        NfcFCardEmulation nfcFCardEmulation = NfcFCardEmulation.getInstance(NfcAdapter.getDefaultAdapter(this));

        // Create a new ComponentName instance pointing to our service.
        ComponentName serviceName = new ComponentName(packageName, serviceClassName);
        
        try {
            // Register the system code for our service.
            success = nfcFCardEmulation.registerSystemCodeForService(serviceName, systemCode);
        } catch(Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Failed to register system code");
        }
        
        return success;
    }
}