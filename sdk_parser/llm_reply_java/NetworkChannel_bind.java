public class NetworkChannel_bind {
    public void test_NetworkChannel_bind() {
        ServerSocketChannel serverSocketChannel = null;
        SocketAddress socketAddress = null;

        try {
            serverSocketChannel = ServerSocketChannel.open();
            socketAddress = new InetSocketAddress("localhost", 8080);  // specifies the IP address and port 

            serverSocketChannel.bind(socketAddress);

            Toast.makeText(this, "Server Socket Channel bound to " + socketAddress.toString(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, "IOException occurred while binding", Toast.LENGTH_LONG).show();
        } finally {
            if (serverSocketChannel != null) {
                try {
                    serverSocketChannel.close();
                } catch (IOException e) {
                    Toast.makeText(this, "IOException occurred while closing channel", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}