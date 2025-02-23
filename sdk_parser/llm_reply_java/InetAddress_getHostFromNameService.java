import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddress_getHostFromNameService {
    public void test_InetAddress_getHostFromNameService() {
        try {
            InetAddress inetAddress = InetAddress.getByName("www.google.com");
            String hostName = inetAddress.getHostName();
            String hostAddress = inetAddress.getHostAddress();
            System.out.println("Host Name: " + hostName);
            System.out.println("Host Address: " + hostAddress);
    
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}