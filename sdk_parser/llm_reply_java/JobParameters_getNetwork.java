import android.app.job.JobParameters;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.ConnectivityManager;

public class JobParameters_getNetwork {
    private boolean test_JobParameters_getNetwork(JobParameters params) {
        Network network = params.getNetwork();

        if (network == null) {
            return false; // No network, job can't continue
        }

        // Check if the network has the TRANSPORT_WIFI capability
        NetworkCapabilities capabilities = getSystemService(ConnectivityManager.class).getNetworkCapabilities(network);

        if (capabilities == null || !capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            return false; // Not WIFI network, job can't continue
        }

        // TODO: perform network operations...

        return true; // job completed
    }
}