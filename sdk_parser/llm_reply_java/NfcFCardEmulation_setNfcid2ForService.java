public class NfcFCardEmulation_setNfcid2ForService {

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void test_NfcFCardEmulation_setNfcid2ForService() {
        //Initialize NfcF Card Emulation.
        NfcFCardEmulation nfcFCardEmulation = NfcFCardEmulation.getInstance(NfcAdapter.getDefaultAdapter(this));
        
        ComponentName componentName = new ComponentName("YOUR_PACKAGE_NAME_HERE", "YOUR_COMPONENT_NAME_HERE");
        String nfcid2 = "YOUR_NFCID2_HERE";
        
        try {
            //Make sure to register a valid Service for NFC in your application's Manifest.xml
            boolean result = nfcFCardEmulation.setNfcid2ForService(componentName, nfcid2);
            
            if(result) {
                Log.d("test", "Successfully registered NFCID2 for service.");
            } else {
                Log.e("test", "Failed to register NFCID2, check your Component Name and/or nfcid2.");
            }
        } catch(SecurityException e) {
            //In case your application does not have the necessary NFC permissions,
            //a SecurityException will be thrown.
            Log.e("test", e.getMessage());
        }
    }
}