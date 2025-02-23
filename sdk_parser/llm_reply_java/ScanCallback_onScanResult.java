public class ScanCallback_onScanResult {
    @Test
    public void test_ScanCallback_onScanResult() {
        // Create the objects needed for the test
        BluetoothDevice testDevice = Mockito.mock(BluetoothDevice.class);
        ScanRecord testRecord = Mockito.mock(ScanRecord.class);
        ScanResult testResult = new ScanResult(testDevice, testRecord, -30, ScanResult.PERIODIC_INTERVAL_NOT_PRESENT, SystemClock.elapsedRealtimeNanos(), ScanCallback.TYPESCANNER_UNDETERMINED, (int)SystemClock.elapsedRealtimeNanos(), 0);

        // Create a spy of your ScanCallback
        ScanCallback testScanCallback = Mockito.spy(new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
            }
        });

        // Call method of ScanCallback
        testScanCallback.onScanResult(ScanSettings.CALLBACK_TYPE_ALL_MATCHES, testResult);

        // Verify that the onScanResult method has been called with the correct parameters
        Mockito.verify(testScanCallback).onScanResult(ScanSettings.CALLBACK_TYPE_ALL_MATCHES, testResult);
    }
}