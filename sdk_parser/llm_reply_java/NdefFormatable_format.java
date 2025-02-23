public class NdefFormatable_format {
    public void test_NdefFormatable_format(NdefFormatable formatable) {
        try {
            // Connect to the NFC tag
            formatable.connect();

            // Create an NDEF message to write on the tag
            NdefRecord ndefRecord = NdefRecord.createTextRecord("en", "Hello World");
            NdefMessage ndefMessage = new NdefMessage(ndefRecord);

            // Format the tag with the message
            formatable.format(ndefMessage);
        } catch (FormatException e) {
            // Handle error during the formatting
            e.printStackTrace();
        } finally {
            // Make sure to close the connection with the tag
            try {
                formatable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}