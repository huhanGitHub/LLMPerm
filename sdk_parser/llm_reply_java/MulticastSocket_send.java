public class MulticastSocket_send {
    public void test_MulticastSocket_send() {
        MulticastSocket socket = null;
        byte[] data = "Hello, World!".getBytes();
        
        try {
            // Create a MulticastSocket
            socket = new MulticastSocket(5000);

            // Determine the IP address of a host, given the host's name
            InetAddress group = InetAddress.getByName("230.0.0.1");

            // Joins a multicast group
            socket.joinGroup(group);

            // Create a DatagramPacket
            DatagramPacket packet = new DatagramPacket(data, data.length, group, 5000);

            // Sends a DatagramPacket to the server
            socket.send(packet);

            // Leaves a multicast group
            socket.leaveGroup(group);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}