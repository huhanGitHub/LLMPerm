public class NfcV_transceive {
    private void test_NfcV_transceive() {
        Tag myTag = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (myTag != null) {
            NfcV nfcvTag = NfcV.get(myTag);

            if (nfcvTag != null) {
                try {
                    nfcvTag.connect();

                    byte[] myCommand = new byte[] {
                            (byte) 0x00, (byte) 0x20, // command: GET_SYSTEM_INFO
                            (byte) 0x00 // parameter: all
                    };

                    byte[] response = nfcvTag.transceive(myCommand);

                    // Print the response in hex
                    String responseInHex = bytesToHex(response);
                    Log.i("Test", "Response in hex: " + responseInHex);

                    nfcvTag.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "No NFCV found. Please scan an NFCV tag.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}