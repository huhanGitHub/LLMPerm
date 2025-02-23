import java.net.NetworkInterface;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Enumeration;
import android.util.Log;

public class NetworkInterface_getInetAddresses {
    public void test_NetworkInterface_getInetAddresses() {
        try {
            // Get all network interfaces of the current system
            Enumeration<NetworkInterface> networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();

            while (networkInterfaceEnumeration.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaceEnumeration.nextElement();
                // Get all addresses for the current network interface
                Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses();

                while (inetAddressEnumeration.hasMoreElements()) {
                    InetAddress inetAddress = inetAddressEnumeration.nextElement();
                    // log each InetAddress
                    Log.i("InetAddress", inetAddress.toString());
                }
            }
        } catch (SocketException e) {
            // Log any SocketException
            Log.e("NetworkInterface", "Error when getting network interfaces", e);
        }
    }
}