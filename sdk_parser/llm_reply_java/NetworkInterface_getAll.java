import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

public class NetworkInterface_getAll {
    public void test_NetworkInterface_getAll() {
        try {
            List<NetworkInterface> allNetworkInterfaces = Collections
                    .list(NetworkInterface.getNetworkInterfaces());

            for (NetworkInterface ni : allNetworkInterfaces) {
                if (ni.isUp()) {
                    System.out.println("Network Interface Name: " + ni.getName());
                    System.out.println("Network Interface Display Name: " + ni.getDisplayName());
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}