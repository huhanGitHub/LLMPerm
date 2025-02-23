public class AsynchronousServerSocketChannel_bind {
    public void test_AsynchronousServerSocketChannel_bind() {
        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            // Step1: Open Asynchronous Server Socket Channel
            AsynchronousServerSocketChannel asyncServerChannel = AsynchronousServerSocketChannel.open();

            // Step2: Bind to a local address
            asyncServerChannel.bind(new InetSocketAddress("localhost", 5000));

            System.out.println("Server: Binding at localhost:5000");

            // Set a completion handler to handle callbacks
            asyncServerChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
                @Override
                public void completed(AsynchronousSocketChannel result, Void attachment) {
                    // Print a message on successful connection
                    System.out.println("Server: Received a connection");

                    try {
                        // Close the connection
                        result.close();
                    } catch ( IOException e ) {
                        e.printStackTrace();
                    }

                    // Accept the next connection
                    asyncServerChannel.accept(null, this);
                }

                @Override
                public void failed(Throwable exc, Void attachment) {
                    // Print a message if the connection attempt failed
                    System.out.println("Server: Failed to accept a connection");
                    exc.printStackTrace();
                }
            });

            // Run the server for 10 seconds
            executorService.schedule(() -> {
                try {
                    System.out.println("Server: Shutting down");
                    asyncServerChannel.close();
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }, 10, TimeUnit.SECONDS);

            // Keep the main thread alive for the server to run
            while( !Thread.interrupted() ) {
                Thread.sleep(1000);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}