public class AsynchronousSocketChannel_connect {
    public void test_AsynchronousSocketChannel_connect() {
        final AsynchronousSocketChannel clientChannel;
        try {
            // Create an AsynchronousSocketChannel
            clientChannel = AsynchronousSocketChannel.open();
            if (!clientChannel.isOpen()) {
                throw new RuntimeException("Unable to open AsynchronousSocketChannel");
            }
    
            // Connect to remote server
            InetSocketAddress hostAddress = new InetSocketAddress("localhost", 5000);
            Future<Void> future = clientChannel.connect(hostAddress);
            try {
                Void result = future.get(5, TimeUnit.SECONDS);
                System.out.println("Connection established");
            } catch (TimeoutException ex) {
                System.out.println("Connection timeout");
                future.cancel(true);
            }
    
            // Close the channel
            clientChannel.close();
        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}