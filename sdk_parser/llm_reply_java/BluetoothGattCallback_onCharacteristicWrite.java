public class BluetoothGattCallback_onCharacteristicWrite {
    public void test_BluetoothGattCallback_onCharacteristicWrite() {
        BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
            @Override
            public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                super.onCharacteristicWrite(gatt, characteristic, status);
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    Log.i("BluetoothTest", "Characteristic successfully written");
                } else {
                    Log.e("BluetoothTest", "Characteristic write unsuccessful, status: " + status);
                    gatt.disconnect();
                }
            }

            @Override
            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                super.onConnectionStateChange(gatt, status, newState);

                if (newState == BluetoothGatt.STATE_CONNECTED) {
                    Log.i("BluetoothTest", "Connected to GATT server.");
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    Log.i("BluetoothTest", "Disconnected from GATT server.");
                }

            }
        };

        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBtIntent);
            return;
        }

        BluetoothDevice device = bluetoothAdapter.getRemoteDevice("Device_Address");
        BluetoothGatt bluetoothGatt = device.connectGatt(this, false, gattCallback);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BluetoothGattService service = bluetoothGatt.getService(UUID.fromString("Service_UUID"));
        if (service == null) {
            Log.e("BluetoothTest", "Service not found!");
            return;
        }

        BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID.fromString("Characteristic_UUID"));
        if (characteristic == null) {
            Log.e("BluetoothTest", "Characteristic not found!");
            return;
        }

        characteristic.setValue(new byte[]{(byte) 0x01});
        if (!bluetoothGatt.writeCharacteristic(characteristic)) {
            Log.e("BluetoothTest", "Failed to write characteristic");
        }
    }
}