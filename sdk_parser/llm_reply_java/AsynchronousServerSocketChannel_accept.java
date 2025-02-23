public class AsynchronousServerSocketChannel_accept {
    @Test
    public void test_AsynchronousServerSocketChannel_accept() {
        final String serverMessage = "Hello client!";
        final String clientMessage = "Hello server!";
        // Configure a thread for the client.
        Thread clientThread = new Thread(() -> {
            try {
                // Make a connection to the server.
                AsynchronousSocketChannel clientChannel = AsynchronousSocketChannel.open();
                clientChannel.connect(new InetSocketAddress("localhost", 5000));
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                // Read a message from the server.
                Integer bytesRead = clientChannel.read(buffer).get();
                String message = new String(buffer.array()).trim();
                // Validate the message.
                assertEquals(serverMessage, message);
                // Send a message to the server.
                buffer.clear();
                buffer.put(clientMessage.getBytes());
                buffer.flip();
                clientChannel.write(buffer);
            } catch (IOException | InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        try {
            // Open the server channel and bind to a port.
            AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(5000));
            // Start the client.
            clientThread.start();
            // Accept a connection.
            Future<AsynchronousSocketChannel> acceptResult = serverChannel.accept();
            AsynchronousSocketChannel workerChannel = acceptResult.get();
            // Send a message to the client.
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(serverMessage.getBytes());
            buffer.flip();
            workerChannel.write(buffer);
            // Read a message from the client.
            buffer.clear();
            workerChannel.read(buffer).get();
            String message = new String(buffer.array()).trim();
            // Validate the message.
            assertEquals(clientMessage, message);
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}