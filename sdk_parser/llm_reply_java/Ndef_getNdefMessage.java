public class Ndef_getNdefMessage {
    public void test_Ndef_getNdefMessage() {
        NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);
        if (adapter != null && adapter.isEnabled()) {
            Tag tag = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if (tag != null) {
                try {
                    Ndef ndef = Ndef.get(tag);
                    if (ndef != null) {
                        ndef.connect();
                        NdefMessage ndefMessage = ndef.getNdefMessage();
                        if (ndefMessage != null) {
                            // Do something with the message
                            // For example, print the first record to Logcat:
                            Log.i("NdefMessage", new String(ndefMessage.getRecords()[0].getPayload()));
                        }
                        ndef.close();
                    }
                } catch (IOException | FormatException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Toast.makeText(this, "NFC is not available on this device.", Toast.LENGTH_SHORT).show();
        }
    }
}