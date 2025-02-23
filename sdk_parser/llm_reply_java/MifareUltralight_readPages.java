public class MifareUltralight_readPages {
    
    public void test_MifareUltralight_readPages(Tag tag) {
        MifareUltralight mifare = MifareUltralight.get(tag);
        try {
            mifare.connect();
            byte[] payload = mifare.readPages(4);
            
            // Print out the payload in hex format
            StringBuilder sb = new StringBuilder();
            for(byte b : payload) {
                sb.append(String.format("%02X ", b));
            }
            Log.i("Payload", "Data: " + sb.toString());
        } catch (IOException e) {
            Log.e("MifareUltralight", "IOException while reading MifareUltralight message...", e);
        } finally {
            if (mifare != null) {
                try {
                    mifare.close();
                } catch (IOException e) {
                    Log.e("MifareUltralight", "Error closing MifareUltralight...", e);
                }
            }
        }
    }
}