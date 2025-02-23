public class NfcB_transceive {
    private void test_NfcB_transceive() {
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "NfcAdapter is not available", Toast.LENGTH_LONG).show();
            return;
        }
        if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, "Please enable your NFC", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(NfcAdapter.ACTION_TAG_DISCOVERED);

        if (intent != null && NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if (tag != null) {
                NfcB nfcB = NfcB.get(tag);
                
                if (nfcB != null) {
                    try {
                        nfcB.connect();
                        if (nfcB.isConnected()) {
                            byte[] command = new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
                            byte[] response = nfcB.transceive(command);
                            
                            String responseAsString = new String(response, StandardCharsets.UTF_8);
                            Log.d("NFC Response", responseAsString);
                        } 
                        nfcB.close();
                    } catch (TagLostException e) {
                        Toast.makeText(this, "TagLostException occurred", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Toast.makeText(this, "IOException occurred", Toast.LENGTH_LONG).show();
                    }
                } else{
                    Toast.makeText(this, "NfcB is not available", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}