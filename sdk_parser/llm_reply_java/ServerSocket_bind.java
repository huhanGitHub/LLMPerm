public class ServerSocket_bind {
    public void test_ServerSocket_bind() {
        try {
            // Create new un-bound server socket
            ServerSocket serverSocket = new ServerSocket();

            // Create a socket address where the server socket will be bound
            InetSocketAddress socketAddress = new InetSocketAddress("localhost", 8080);

            // Bind the server socket to the specified address
            serverSocket.bind(socketAddress);

            // Wait for client connection
            Socket clientSocket = serverSocket.accept();
            Log.d("Test", "Client connected: " + clientSocket.getInetAddress());

            // Work with the connected client 
            // For illustration - just close the client socket
            clientSocket.close();

            // Close the server socket after use
            serverSocket.close();
        } catch (IOException ex) {
            Log.e("Test", "Server Socket error: " + ex.getMessage());
        }
    }
}