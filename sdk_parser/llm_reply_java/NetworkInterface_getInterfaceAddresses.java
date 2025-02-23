import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import android.util.Log;

public class NetworkInterface_getInterfaceAddresses {
    public void test_NetworkInterface_getInterfaceAddresses() {
        try {
            for (NetworkInterface networkInterface : 
                     Collections.list(NetworkInterface.getNetworkInterfaces())) {
                Log.i("NetworkTest", networkInterface + "\n");
                Log.i("NetworkTest", "Name: " + networkInterface.getName() + "\n");
                Log.i("NetworkTest", "Addresses: " + networkInterface.getInterfaceAddresses() + "\n");
            }
        } catch (SocketException e) {
            Log.e("NetworkTest", "Error getting network interfaces", e);
        }
    }
}