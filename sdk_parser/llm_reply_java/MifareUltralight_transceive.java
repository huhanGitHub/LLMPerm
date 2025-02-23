public class MifareUltralight_transceive {

    public void test_MifareUltralight_transceive(Tag tag) {
        MifareUltralight mifare = MifareUltralight.get(tag);

        try {
            mifare.connect();

            // Construct command details
            byte[] payload = new byte[4];
            // set command payload.. e.g write to page 4
            payload[0] = 1;   //page address
            payload[1] = 'A'; //data
            payload[2] = 'B'; //data
            payload[3] = 'C'; //data

            byte[] command = new byte[16];
            // Set command type (e.g., read or write)
            command[0] = (byte) 0xA2; //COMMAND_WRITE
            System.arraycopy(payload, 0, command, 1, payload.length);

            byte[] response = mifare.transceive(command);
            // transceive returns response from tag

            Log.i(TAG, "Response from tag: " + Arrays.toString(response));

        } catch (IOException e) {
            Log.e(TAG, "IOException while using MifareUltralight", e);
        } finally {
            try {
                mifare.close();
            } catch (IOException e) {
                Log.e(TAG, "IOException while closing MifareUltralight", e);
            }
        }
    }
}