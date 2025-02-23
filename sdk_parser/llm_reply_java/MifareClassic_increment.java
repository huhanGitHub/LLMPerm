public class MifareClassic_increment {
    public void test_MifareClassic_increment() {
        // First, obtain a tag from the intent
        Tag tag = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
        
        try {
            // Get an instance of the MifareClassic class
            MifareClassic mifare = MifareClassic.get(tag);
            
            // Connect to the tag 
            mifare.connect();

            // Authenticate with key A (For communication with the tag)
            boolean authenticated = mifare.authenticateSectorWithKeyA(1, MifareClassic.KEY_DEFAULT);
            if (authenticated) {
                // Read the data
                int value = mifare.getValue(4);
                // Increment the value
                mifare.increment(4, value + 1);
                // Store the new value
                mifare.transfer(4);
                // Read the data again
                int newValue = mifare.getValue(4);

                // Print out the new value
                Log.d("MifareClassicExample", "New Value: " + newValue);
            } else {
                // Authentication failed - Handle it
                Log.e("MifareClassicExample", "Authentication with KeyA failed!");
            }

            // After interacting with a tag, you must call close()
            mifare.close();

        } catch(IOException e) {
            Log.e("MifareClassicExample", "IOException while trying to increment MifareClassic", e);
        } catch (Exception e) {
            Log.e("MifareClassicExample", "Failed to increment MifareClassic", e);
        }
    }
}