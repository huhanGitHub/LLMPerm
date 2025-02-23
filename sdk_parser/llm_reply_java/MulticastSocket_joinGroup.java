public class MulticastSocket_joinGroup {
    public void test_MulticastSocket_joinGroup() {
        MulticastSocket multicastSocket = null;
        InetAddress groupAddress = null;
        
        try {
            // Initialize MulticastSocket on port 1234
            multicastSocket = new MulticastSocket(1234);
            
            // Provide the IP address of the group you want to join
            groupAddress = InetAddress.getByName("230.0.0.1");
            
            // Join Group on given IP
            multicastSocket.joinGroup(groupAddress);
            
            Log.d("MulticastTest", "Successfully joined the multicast group");
            
            //code here to handle socket data
            
            // Always remember to leave the group when you are done
            multicastSocket.leaveGroup(groupAddress);
            Log.d("MulticastTest", "Successfully left the multicast group");
            
        } catch (IOException exception) {
            Log.e("MulticastTest", "Error occurred when joining or leaving a multicast group");
            exception.printStackTrace();
        } finally {
            if (multicastSocket != null) {
                multicastSocket.close();
            }
        }
    }
}