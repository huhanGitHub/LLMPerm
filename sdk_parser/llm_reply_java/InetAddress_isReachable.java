import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddress_isReachable {

    public boolean test_InetAddress_isReachable(String address) {
        try {
            InetAddress inetAddress = InetAddress.getByName(address);
            boolean reachable = inetAddress.isReachable(2000);
            return reachable;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}