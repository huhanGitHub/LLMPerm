public class ServerSocket_setOption {
    public void test_ServerSocket_setOption() {
        // Create a new thread to avoid NetworkOnMainThreadException
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocket serverSocket = null;
                try {
                    // Create a new ServerSocket on port 1234
                    serverSocket = new ServerSocket(1234);

                    // Set the SO_TIMEOUT option for the ServerSocket
                    // The subsequent call to accept() will block for maximum of 5000ms (5 secs)
                    serverSocket.setSoTimeout(5000);

                    // Use the serverSocket
                    // ...

                } catch (IOException e) {
                    // Handle exceptions
                    e.printStackTrace();
                } finally {
                    if(serverSocket != null) {
                        try {
                            serverSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        // Start the thread
        thread.start();

        try {
            // Wait for the thread to finish
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}