public class IsoDep_transceive {
    public void test_IsoDep_transceive() {
        try {
            // Create an NFC adapter instance
            NfcAdapter mAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());

            // Check if NFC is enabled on the device
            if (mAdapter.isEnabled()) {
                // Get the Intent that started this activity
                Intent intent = getIntent();

                // Get the Tag object from the Intent and create IsoDep instance
                Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                IsoDep isoDep = IsoDep.get(tagFromIntent);
            
                try {
                    // Connect to the remote NFC device
                    isoDep.connect();

                    // Transceives data from one NFC device to another
                    byte[] command = new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
                    byte[] response = isoDep.transceive(command);

                    // Do something with the response
                    // ...

                } catch (IOException e) {
                    Log.e("IsoDep transceive", "IOExcetion: " + e.getMessage());
                } finally {
                    if(isoDep.isConnected()) {
                        isoDep.close();
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), "Please enable NFC and try again", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e("IsoDep transceive", "Exception: " + e.getMessage());
        }
    }
}