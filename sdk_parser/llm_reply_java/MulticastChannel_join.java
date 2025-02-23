import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.channels.MulticastChannel;
import java.nio.channels.DatagramChannel;
import java.nio.channels.MembershipKey;
import java.net.StandardSocketOptions;

public class MulticastChannel_join {
    public void test_MulticastChannel_join() {
        InetAddress group = null;
        NetworkInterface ni = null;
        try {
            group = InetAddress.getByName("225.4.5.6");
            ni = NetworkInterface.getByName("wlan0");
        } catch (IOException e) {
            System.out.println("Error setting up MulticastChannel: " + e.getMessage());
        }
    
        try (DatagramChannel dc = DatagramChannel.open()){
            dc.bind(null);
            dc.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            MulticastChannel mc = (MulticastChannel) dc;
            if (group != null && ni != null) {
                MembershipKey key = mc.join(group, ni);
                System.out.println("Joined group " + group + " on interface " + ni);
                key.drop();
                System.out.println("Dropped group " + group + " on interface " + ni);
            }
        } catch (IOException e) {
            System.out.println("Error joining or dropping multicast group: " + e.getMessage());
        }
    }
}