public class MifareClassic_writeBlock {

    public boolean test_MifareClassic_writeBlock(NfcAdapter nfcAdapter, Intent intent, int blockIndex, byte[] data) {
        MifareClassic mifareClassic = MifareClassic.get(nfcAdapter.getTag());
        boolean success = false;

        if (mifareClassic != null) {
            try {
                mifareClassic.connect();
                boolean auth = false;
                byte[] defaultKey = MifareClassic.KEY_DEFAULT;

                auth = mifareClassic.authenticateSectorWithKeyA(mifareClassic.blockToSector(blockIndex), defaultKey);

                if (auth) {
                    // Write data to block
                    mifareClassic.writeBlock(blockIndex, data);
                    
                    success = true;
                    Log.d("MifareClassic", "Write Block Success!");
                } else {
                    Log.d("MifareClassic", "Failed to authenticate with tag.");
                }

            } catch (IOException e) {
                Log.e("MifareClassic", "IOException while writing MifareClassic message...", e);
            } finally {
                if (mifareClassic != null) {
                    try {
                        mifareClassic.close();
                    } catch (IOException e) {
                        Log.e("MifareClassic", "Error closing tag...", e);
                    }
                }
            }
        }

        return success;
    }
}