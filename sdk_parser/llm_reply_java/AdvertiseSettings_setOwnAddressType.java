public class AdvertiseSettings_setOwnAddressType {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @TargetApi(Build.VERSION_CODES.O)
    public void test_AdvertiseSettings_setOwnAddressType() {
        Context context = mock(Context.class);
        BluetoothManager bluetoothManager = mock(BluetoothManager.class);
        BluetoothAdapter bluetoothAdapter = mock(BluetoothAdapter.class);
        BluetoothLeAdvertiser bluetoothLeAdvertiser = mock(BluetoothLeAdvertiser.class);

        when(context.getSystemService(Context.BLUETOOTH_SERVICE)).thenReturn(bluetoothManager);
        when(bluetoothManager.getAdapter()).thenReturn(bluetoothAdapter);
        when(bluetoothAdapter.getBluetoothLeAdvertiser()).thenReturn(bluetoothLeAdvertiser);

        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
            if (!bluetoothAdapter.isMultipleAdvertisementSupported()) {
                return;
            }
        }

        int ownAddressType = AdvertiseSettings.ADVERTISE_TX_POWER_HIGH;
        AdvertiseSettings advertiseSettings = new AdvertiseSettings.Builder()
                .setOwnAddressType(ownAddressType)
                .build();

        bluetoothLeAdvertiser.startAdvertising(advertiseSettings, new AdvertiseData.Builder().build(),
                new AdvertiseCallback() {
                    @Override
                    public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                        System.out.println("Successfully started advertising.");
                    }

                    @Override
                    public void onStartFailure(int errorCode) {
                        System.out.println("Failed to start advertising with error code: " + errorCode);
                    }
                }
        );
    }
}