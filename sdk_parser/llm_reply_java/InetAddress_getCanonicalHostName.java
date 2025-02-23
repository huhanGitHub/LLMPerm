public class InetAddress_getCanonicalHostName {
    public void test_InetAddress_getCanonicalHostName() {
        try {
            // Get local IP address
            InetAddress myIP = InetAddress.getLocalHost();

            // Get canonical host name
            String canonicalHostName = myIP.getCanonicalHostName();
            
            // Print canonical host name
            System.out.println("Canonical Host Name: " + canonicalHostName);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}