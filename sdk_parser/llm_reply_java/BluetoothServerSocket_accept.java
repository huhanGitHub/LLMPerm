import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import java.io.IOException;
import java.util.UUID;

public class BluetoothServerSocket_accept {
    // Testing BluetoothServerSocket's accept method
    public void test_BluetoothServerSocket_accept(){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothServerSocket mBluetoothServerSocket = null;
        BluetoothSocket mBluetoothSocket = null;

        // Universal Well Known UUID for this application
        UUID MY_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");

        // Using the well known SPP UUID
        UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

        try {
            // MY_UUID is the app's UUID string, also used by the client code
            mBluetoothServerSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("YourAppName", MY_UUID);
        } catch (IOException e) {
            Log.e("SERVER_SOCKET", "Socket's listen() method failed", e);
        }

        try {
            // This is a blocking call and will only return on a
            // successful connection or an exception
            mBluetoothSocket = mBluetoothServerSocket.accept();
        } catch (IOException e) {
            Log.e("SERVER_SOCKET", "Socket's accept() method failed", e);
        }

        if (mBluetoothSocket != null) {
            // A connection was accepted. Perform work associated with
            // the connection in a separate thread
            manageMyConnectedSocket(mBluetoothSocket);

            try {
                mBluetoothServerSocket.close();
            } catch (IOException e) {
                Log.e("SERVER_SOCKET", "Could not close the connect socket", e);
            }
        }
    }

    // This method is used to do the actual data transfer process
    private void manageMyConnectedSocket(BluetoothSocket mSocket) {
        // Do work to manage the connection (in a separate thread)
        // write code to send and receive data from your Bluetooth other device.
    }
}