public class DatagramSocket_send {
    public void test_DatagramSocket_send() {
        DatagramSocket socket = null;
        try {
            // Create a DatagramSocket
            socket = new DatagramSocket();

            // Define a server host and port number
            InetAddress serverIP = InetAddress.getByName("your_server_address_here");
            int serverPort = 8888;

            // The message to send
            String message = "Test message";
            byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);

            // Create a DatagramPacket to send
            DatagramPacket packet = new DatagramPacket(
                    messageBytes,
                    messageBytes.length,
                    serverIP,
                    serverPort);

            // Send the packet
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}