public class MifareClassic_authenticateSectorWithKeyA {
    public boolean test_MifareClassic_authenticateSectorWithKeyA(Tag tag) {
        MifareClassic mifareClassic = MifareClassic.get(tag);
        boolean auth = false;
        try {
            mifareClassic.connect();

            // Mifare Classic card block index: 0, sector: 0, Key A
            byte[] keyA = MifareClassic.KEY_DEFAULT;
            
            int sectorIndex = 0;
            auth = mifareClassic.authenticateSectorWithKeyA(sectorIndex, keyA);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (mifareClassic != null) {
                try {
                    mifareClassic.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return auth;
    }
}