public class BluetoothServerSocket_getPsm {

    public void test_BluetoothServerSocket_getPsm() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
            BluetoothServerSocket serverSocket = null;
            try {
                // Create a secure incoming RFCOMM Bluetooth connection
                serverSocket = bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord("Test", UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) { 
                    // get the PSM for the L2CAP server socket
                    int psm = serverSocket.getPsm();
                    Log.d("BluetoothServerSocket", "PSM: " + psm);
                } else {
                    Log.d("BluetoothServerSocket", "Android Q or higher is required to get PSM.");
                }
            } catch (IOException e) {
                Log.e("BluetoothServerSocket", "Socket's listen() method failed", e);
            } finally {
                if (serverSocket != null) {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        Log.e("BluetoothServerSocket", "Could not close the connect socket", e);
                    }
                }
            }
        } else {
            Log.d("BluetoothServerSocket", "Bluetooth is not available or not enabled.");
        }
    }
}