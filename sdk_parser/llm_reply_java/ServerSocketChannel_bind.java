public class ServerSocketChannel_bind {
    public void test_ServerSocketChannel_bind() {
        ServerSocketChannel serverChannel = null;

        try {
            // Initialize a new server socket channel
            serverChannel = ServerSocketChannel.open();

            // Ensure it's non blocking
            serverChannel.configureBlocking(false);

            // Create an address object
            InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 8000);

            // Bind the server socket to local host and port 8000
            serverChannel.socket().bind(inetSocketAddress);

            System.out.println("Server socket channel binding is successful!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverChannel != null) {
                try {
                    // Close the server channel
                    serverChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}