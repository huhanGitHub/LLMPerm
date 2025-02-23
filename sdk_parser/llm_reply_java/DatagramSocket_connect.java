public class DatagramSocket_connect {
    public void test_DatagramSocket_connect() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket socket = null;
                try {
                    // Initialize a DatagramSocket
                    socket = new DatagramSocket();

                    // Connect to a remote address
                    InetAddress serverAddr = InetAddress.getByName("8.8.8.8");
                    int serverPort = 53;
                    socket.connect(serverAddr, serverPort);

                    // The socket is now connected and can be used for sending and receiving data
                    byte[] data = "test data".getBytes(Charset.defaultCharset());
                    DatagramPacket packet = new DatagramPacket(data, data.length);
                    socket.send(packet);

                } catch (Exception e) {
                     // Handle exceptions
                    e.printStackTrace();
                } finally {
                    if (socket != null) {
                        socket.close();
                    }
                }
            }
        });

        thread.start();
    }
}