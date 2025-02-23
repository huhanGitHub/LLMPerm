public class ServerSocketFactory_createServerSocket {
    public void test_ServerSocketFactory_createServerSocket() {
        try {
            // Get the ServerSocketFactory
            ServerSocketFactory factory = ServerSocketFactory.getDefault();

            // Create a server socket bound to a specific port
            ServerSocket serverSocket = factory.createServerSocket(8000);

            System.out.println("Server socket created and listening on port 8000");

            // It's important to close the server socket when it's no longer needed
            serverSocket.close();

        } catch (IOException e) {
            System.out.println("Error creating the server socket: " + e.getMessage());
        }
    }
}