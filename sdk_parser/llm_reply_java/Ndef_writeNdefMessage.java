public class Ndef_writeNdefMessage {
    public void test_Ndef_writeNdefMessage() {
        // create new NDEF message
        NdefMessage message = new NdefMessage(new NdefRecord[]{NdefRecord.createUri(Uri.parse("https://www.example.com"))});

        // get a reference to the NFC adapter
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        // get an intent for NFC tag discovered action
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        // create intent filter for all NDEF discovered intents
        IntentFilter ndefIntentFilter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);

        try {
            // catch all NDEF discovery intents
            ndefIntentFilter.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("could not add MIME type", e);
        }

        // set up NDEF message to be written when a tag is discovered
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, new IntentFilter[]{ndefIntentFilter}, null);
        nfcAdapter.setNdefPushMessage(message, this);
    }
}