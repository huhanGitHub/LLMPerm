public class SocketHandler_connect {
    public void test_SocketHandler_connect() {
        try {
            // Create a SocketHandler. The host name and TCP port number need to be specified in the form "hostname:port".
            // For this test, we'll use localhost:5000.
            SocketHandler handler = new SocketHandler("localhost", 5000);

            // Log a simple INFO message.
            handler.publish(new LogRecord(Level.INFO, "Test message"));

            // Close the Handler to free resources.
            handler.close();
        } catch (IOException e) {
            // Handle error: could not connect to host
            e.printStackTrace();
        } catch (SecurityException e) {
            // Handle error: security violation occurred
            e.printStackTrace();
        }
    }
}