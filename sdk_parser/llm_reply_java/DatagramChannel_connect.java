import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class DatagramChannel_connect {

    private DatagramChannel channel;

    public void test_DatagramChannel_connect() throws IOException {
        // Create a new DatagramChannel
        channel = DatagramChannel.open();

        // Connect to the server at localhost on port 8000
        channel.connect(new InetSocketAddress("localhost", 8000));
        
        // Create a ByteBuffer to hold data to be sent to the server
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // Write data to the buffer
        buffer.put("Hello, World!".getBytes());
        buffer.flip();

        // Send the buffer to the server
        channel.write(buffer);

        // Now receive data
        buffer.clear();
        channel.read(buffer);

        // Flip the buffer to read it
        buffer.flip();

        // Convert the data into a string
        String receivedData = new String(buffer.array(), 0, buffer.limit());

        // Print the received data
        System.out.println(receivedData);
        
        // Finally close the channel
        channel.close();
    }
}