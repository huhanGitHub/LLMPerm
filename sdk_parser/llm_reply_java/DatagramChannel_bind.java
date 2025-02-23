import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;

public class DatagramChannel_bind {
    public void test_DatagramChannel_bind() {
        DatagramChannel datagramChannel = null;

        try {
            // Create a new datagram channel
            datagramChannel = DatagramChannel.open();

            // Bind the datagram channel to local address
            InetSocketAddress address = new InetSocketAddress("localhost", 8000);
            datagramChannel.bind(address);

            // Print the statement for check whether it is bound or not
            System.out.println("DatagramChannel is bound to: " + datagramChannel.getLocalAddress());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (datagramChannel != null) {
                try {
                    // Close the datagram channel
                    datagramChannel.close();
                    System.out.println("DatagramChannel is closed");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}