public class IsoDep_setTimeout {

    public void test_IsoDep_setTimeout() {
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter != null) {
            Intent nfcIntent = new Intent(this, getClass());
            nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, nfcIntent, 0);

            IntentFilter isoDepDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);

            try {
                isoDepDetected.addDataType("*/*");
            } catch (IntentFilter.MalformedMimeTypeException e) {
                throw new RuntimeException("Could not add MIME type.", e);
            }

            IntentFilter[] intentFiltersArray = new IntentFilter[]{isoDepDetected};
            String[][] techList = new String[][]{new String[]{IsoDep.class.getName()}};
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techList);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
            Tag detectedTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            IsoDep isoDepInstance = IsoDep.get(detectedTag);
            if (isoDepInstance != null) {
                try {
                    isoDepInstance.connect();
                    isoDepInstance.setTimeout(5000);  // set timeout to 5000 milliseconds
                    // Now you can transceive with the IsoDep tag...
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        isoDepInstance.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}