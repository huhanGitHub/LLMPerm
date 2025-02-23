public class ServerSocket_getOption {
    public void test_ServerSocket_getOption() {
        int port = 8080;
        try {
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(port);

            // Get the value of an option for the server socket
            int receiveBuffer = (int) serverSocket.getOption(StandardSocketOptions.SO_RCVBUF);

            // Close the server socket
            serverSocket.close();

            // Print the value of the receive buffer size option
            System.out.println("Receive Buffer size: " + receiveBuffer);
        } catch (IOException e) {
            // Print the stack trace for the exception
            e.printStackTrace();
        }
    }
}