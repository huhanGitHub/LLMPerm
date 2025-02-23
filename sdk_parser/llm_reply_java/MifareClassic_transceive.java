public class MifareClassic_transceive {
    private static final String TAG = "MifareClassicTransceive";

    private void test_MifareClassic_transceive() {
        try {
            NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);

            if (nfcAdapter == null) {
                // NFC is not available on this device
                return;
            }

            Intent intent = new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);

            Tag tag = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
            MifareClassic mifareClassic = MifareClassic.get(tag);

            if (mifareClassic == null) {
                // Mifare Classic is not supported on this Tag
                return;
            }

            mifareClassic.connect();

            byte[] transceiveData = new byte[]{0x00, 0x01, 0x02, 0x03}; 

            // set the block to transceive
            int blockIndex = 0; 

            // authenticate a block with key A
            byte[] keyA = MifareClassic.KEY_DEFAULT;
            boolean auth = mifareClassic.authenticateSectorWithKeyA(blockIndex / 4, keyA);
            if (auth) {
                // the data is transceived here
                byte[] response = mifareClassic.transceive(transceiveData);
                Log.d(TAG, "Response: " + Arrays.toString(response));
            } else {
                // authentication failed
                Log.e(TAG, "Authentication failed");
            }

            mifareClassic.close();
        } catch (IOException e) {
            Log.e(TAG, "IOException while handling MifareClassic tag", e);
        }
    }
}