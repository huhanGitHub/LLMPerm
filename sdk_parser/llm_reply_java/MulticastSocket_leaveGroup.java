public class MulticastSocket_leaveGroup {
    public void test_MulticastSocket_leaveGroup() {
        MulticastSocket mcastSock = null;
        InetAddress groupAddress = null;

        try {
            // Create the group address object
            groupAddress = InetAddress.getByName("224.0.0.1");

            // Create a new multicast socket on port 6789
            mcastSock = new MulticastSocket(6789);
            
            // Join the group 
            mcastSock.joinGroup(groupAddress);    

            // Now leave the group
            mcastSock.leaveGroup(groupAddress);
            
            // Print status 
            System.out.println("Successfully left group");

        } catch (UnknownHostException ex) {
            System.out.println("UnknownHostException: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("IOException: " + ex.getMessage());
        } finally {
            if (mcastSock != null) {
                try {
                    // Always ensure to close the socket
                    mcastSock.close();
                } catch (Exception ex) {
                    System.out.println("Exception during closing socket: " + ex.getMessage());
                }
            }
        }
    } 
}