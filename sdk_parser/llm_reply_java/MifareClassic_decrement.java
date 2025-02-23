public class MifareClassic_decrement {
    public void test_MifareClassic_decrement() {
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, "Please enable NFC", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag == null) {
            Toast.makeText(this, "No NFC tag detected", Toast.LENGTH_SHORT).show();
            return;
        }

        MifareClassic mifareClassicTag = MifareClassic.get(tag);
        if (mifareClassicTag == null) {
            Toast.makeText(this, "This tag is not a Mifare Classic tag", Toast.LENGTH_SHORT).show();
            return;
        } 

        try {
            byte[] defaultPassword = MifareClassic.KEY_DEFAULT;
            final int MIFARE_BLOCK_NUMBER = 10;

            mifareClassicTag.connect();
            final boolean isAuthenticatedBlock = mifareClassicTag.authenticateSectorWithKeyA(1, defaultPassword);
            if (!isAuthenticatedBlock) {
                Toast.makeText(this, "Failed to authenticate tag", Toast.LENGTH_SHORT).show();
                return;
            }

            final int decrementValue = 1;
            mifareClassicTag.decrement(MIFARE_BLOCK_NUMBER, decrementValue);
            mifareClassicTag.transfer(MIFARE_BLOCK_NUMBER);
            mifareClassicTag.close();

            Toast.makeText(this, "Successfully decremented value", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "IOException while accessing MifareClassic tag", Toast.LENGTH_LONG).show();
        }
    }
}