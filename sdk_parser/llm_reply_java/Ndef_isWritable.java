public class Ndef_isWritable {
    public void test_Ndef_isWritable() {
        NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);
        if (adapter == null) {
            // NFC is not supported on this device.
            return;
        }
        if (!adapter.isEnabled()) {
            // NFC is disabled. 
            return;
        }
        Intent intent = getIntent();
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag != null) {
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                boolean isWritable = ndef.isWritable();
                // You can now use the isWritable variable to check if the NFC tag is writable.
                Toast.makeText(this, "NFC Tag is " + (isWritable ? "Writable" : "Not Writable"), Toast.LENGTH_LONG).show();
            }
        }
    }
}