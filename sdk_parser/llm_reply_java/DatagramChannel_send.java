public class DatagramChannel_send {
    public void test_DatagramChannel_send() {
        DatagramChannel datagramChannel = null;
        try {
            // Create a new datagram channel
            datagramChannel = DatagramChannel.open();

            // Bind the channel's socket
            datagramChannel.bind(null);

            // The address and port of the server to send to
            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 8000;
            SocketAddress target = new InetSocketAddress(serverAddress, serverPort);

            // The message to send
            String message = "Hello, World!";
            ByteBuffer buffer = ByteBuffer.allocate(64);
            buffer.clear();
            buffer.put(message.getBytes());

            // Prepare the buffer to read
            buffer.flip();

            // Send the message
            int bytesSent = datagramChannel.send(buffer, target);
            System.out.println("Sent " + bytesSent + " bytes to server.");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            // Close the channel
            if(datagramChannel!=null){
                try {
                    datagramChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}