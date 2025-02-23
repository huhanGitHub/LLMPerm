public class DatagramSocket_receive {
    public void test_DatagramSocket_receive() {
        try {
            // Create a DatagramSocket
            DatagramSocket datagramSocket = new DatagramSocket(12345);

            // Create a DatagramPacket to receive data
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            // Set a receive timeout, if you wish (in milliseconds)
            datagramSocket.setSoTimeout(5000);

            try {
                // Receive data into the DatagramPacket
                datagramSocket.receive(packet);

                // The data received. Note that the packet's length is now the actual length of the data
                byte[] dataReceived = Arrays.copyOf(packet.getData(), packet.getLength());

                // Processing or using the data received
                System.out.println("Data received: " + new String(dataReceived, StandardCharsets.UTF_8));

            } catch (SocketTimeoutException e) {
                // This exception will be thrown if a timeout was set and no packet was received within the timeout
                System.out.println("Time out, no response received.");
            }

            // Always close the DatagramSocket
            datagramSocket.close();

        } catch (IOException e) {
            // Handle any IO Exceptions
            e.printStackTrace();        
        }
    }
}