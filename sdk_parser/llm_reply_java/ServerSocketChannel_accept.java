public class ServerSocketChannel_accept {
    public void test_ServerSocketChannel_accept() {
        // Create a new thread to prevent blocking the UI thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocketChannel serverSocketChannel = null;
                SocketChannel socketChannel = null;

                try {
                    // Open the server socket channel and bind it to an automatic available port
                    serverSocketChannel = ServerSocketChannel.open();
                    serverSocketChannel.socket().bind(new InetSocketAddress(0));
                    
                    // Define sockets blocks when accepting connections.
                    serverSocketChannel.configureBlocking(true);
                    
                    Log.i("ServerSocketChannel", "Server is waiting for client on port " + serverSocketChannel.socket().getLocalPort());
                    
                    // Blocking operation, will return a socket channel for each new client
                    socketChannel = serverSocketChannel.accept();

                    Log.i("ServerSocketChannel", "Connected with client on port " + socketChannel.socket().getPort());                
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (socketChannel != null) {
                            socketChannel.close();
                        }
                        if (serverSocketChannel != null) {
                            serverSocketChannel.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}