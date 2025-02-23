public class SocketChannel_connect {

    private void test_SocketChannel_connect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SocketChannel socketChannel = null;
                try {
                    // replace with your server ip and port
                    SocketAddress socketAddress = new InetSocketAddress("192.168.1.1", 8080);

                    // opens a socket channel
                    socketChannel = SocketChannel.open();
                    // initiates a non-blocking connection operation
                    socketChannel.configureBlocking(false);
                    socketChannel.connect(socketAddress);

                    while(!socketChannel.finishConnect()) {
                        // wait, or do something useful here
                        System.out.println("connecting to the server...");
                    }

                    // sending message to server
                    String sendText = "Hello, server!";
                    ByteBuffer buffer = ByteBuffer.wrap(sendText.getBytes());
                    socketChannel.write(buffer);

                    buffer.clear();
                    socketChannel.close();
                    
                    // log connection successful
                    System.out.println("connected and message sent to the server!");

                } catch (IOException e) {
                    System.err.println("Cannot connect to the server!" + e);
                } finally {
                    // cleaning up
                    if (socketChannel != null) {
                        try {
                            socketChannel.close();
                        } catch (IOException e) {
                            System.err.println("Cannot close the socketChannel" + e);
                        }
                    }
                }
            }
        }).start();
    }
}