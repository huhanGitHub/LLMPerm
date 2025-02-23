public class MifareClassic_readBlock {
    public void test_MifareClassic_readBlock() {
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        
        if(!nfcAdapter.isEnabled()) {
            Toast.makeText(this, "Please enable NFC", Toast.LENGTH_LONG).show();
            return;
        } 
        
        PendingIntent pendingIntent = PendingIntent.getActivity(
            this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        IntentFilter ndefIntent = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);

        try {
            ndefIntent.addDataType("*/*");
            IntentFilter[] intentFiltersArray = new IntentFilter[] { ndefIntent };
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, new String[][] { new String[] { MifareClassic.class.getName() } });
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())) {
            Tag tagFromIntent = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
            MifareClassic mifareClassicTag = MifareClassic.get(tagFromIntent);

            try {
                mifareClassicTag.connect();
                boolean auth = mifareClassicTag.authenticateSectorWithKeyA(1, MifareClassic.KEY_DEFAULT);

                if (auth) {
                    byte[] data = mifareClassicTag.readBlock(0);
                    // Do something with data
                } 

                mifareClassicTag.close();
            } catch (IOException e) {
                // Handle exception
                e.printStackTrace();
            }
        }
    }
}