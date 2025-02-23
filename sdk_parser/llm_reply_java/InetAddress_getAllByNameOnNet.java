import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddress_getAllByNameOnNet {

    public void test_InetAddress_getAllByNameOnNet() {
        try {
            //Here,"www.google.com" is the host you want to get all the InetAddresses.
            InetAddress[] inetAddresses = InetAddress.getAllByName("www.google.com");

            for (InetAddress inetAddress : inetAddresses) {
                System.out.println("HostAddress: " + inetAddress.getHostAddress());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}