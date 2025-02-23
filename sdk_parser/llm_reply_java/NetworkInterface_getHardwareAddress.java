import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetworkInterface_getHardwareAddress {

    public void test_NetworkInterface_getHardwareAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while(networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if(networkInterface.getHardwareAddress() != null) {
                    StringBuilder builder = new StringBuilder();
                    for(byte b : networkInterface.getHardwareAddress()){
                        builder.append(String.format("%02X:",b));
                    }
                    if(builder.length() > 0){
                        builder.deleteCharAt(builder.length() - 1);
                    }
                    System.out.println("Hardware Address of Interface " + networkInterface.getName() + " : " + builder.toString());
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}