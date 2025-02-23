import java.net.DatagramSocket;
import java.net.SocketException;

public class DatagramSocket_getOption {

    public void test_DatagramSocket_getOption() throws SocketException {
        // Create a DatagramSocket
        DatagramSocket datagramSocket = new DatagramSocket();

        // Set the SO_TIMEOUT value
        datagramSocket.setSoTimeout(5000);

        // Get the SO_TIMEOUT option
        int timeout = datagramSocket.getSoTimeout();

        // Print the SO_TIMEOUT option
        System.out.println("The current SO_TIMEOUT value is: " + timeout);

        // Close the DatagramSocket
        datagramSocket.close();
    }
}