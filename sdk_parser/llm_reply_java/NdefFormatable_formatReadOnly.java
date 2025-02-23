public class NdefFormatable_formatReadOnly {
    public void test_NdefFormatable_formatReadOnly(final Tag tag) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Fetch the NdefFormatable instance for the tag
                    NdefFormatable formatable = NdefFormatable.get(tag);

                    if (formatable != null) {
                        try {
                            // Connect to the tag
                            formatable.connect();

                            // Create a new NDEF message to write to the tag
                            NdefMessage ndefMessage = new NdefMessage(NdefRecord.createUri("https://google.com"));

                            try {
                                // Makes NFC read-only 
                                // Note: This is irreversible operation. Once you made the tag read-only, 
                                // you can't write new data to it anymore
                                formatable.formatReadOnly(ndefMessage);
                            } catch (Exception e) {
                                // Handle exception when formatting tag to read-only fails
                                Log.e("TAG_FORMAT", "Failed to format tag to read-only", e);
                            }
                        } catch (IOException e) {
                            // Handle exception when connection to tag fails
                            Log.e("TAG_FORMAT", "Connection to tag failed", e);
                        } finally {
                            formatable.close();
                        }
                    }
                } catch (Exception e) {
                    // Handle general exception
                    Log.e("TAG_FORMAT", "Error formatting tag", e);
                }
            }
        }).start();
    }
}