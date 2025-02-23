public class Network_connectToHost {
    public void test_Network_connectToHost() {
        ConnectivityManager connectivityManager = 
            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        builder.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        builder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR);

        connectivityManager.requestNetwork(builder.build(), new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                // the network is now available
                Log.i("Network", "Network available: " + network);
                connectivityManager.bindProcessToNetwork(network);

                // Perform networking operations here...
                // For instance, connect to a host
            }

            @Override
            public void onLost(Network network) {
                // the network has lost
                Log.i("Network", "Network lost: " + network);
                connectivityManager.bindProcessToNetwork(null);
            }
        });
    }
}