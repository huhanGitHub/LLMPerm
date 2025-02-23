import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkInfo_isConnectedOrConnecting {

    public void test_NetworkInfo_isConnectedOrConnecting() {
        // Get the ConnectivityManager instance
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Check if the service instance is not null
        if (connectivityManager != null) {
            // Get the active network info
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

            // Check if the network info is not null and isConnectedOrConnecting method returns true
            if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()) {
                System.out.println("Device is currently connected to a network, or is in the process of connecting");
            } else {
                System.out.println("Device is not connected to a network, or is not in the process of connecting");
            }
        } else {
            System.out.println("Failed to get the ConnectivityManager instance");
        }
    }
}