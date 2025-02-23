public class SSLServerSocketFactory_createServerSocket {
    public void test_SSLServerSocketFactory_createServerSocket() {
        int serverPort = 443; // You can specify your port here
        SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

        try {
            // this will create SSL server socket on the specified port with 0 backlog
            SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(serverPort);
            
            // set client authentication to false since we do not need client side certificate
            sslServerSocket.setNeedClientAuth(false);
            System.out.println("SSL Server Socket is created successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}