public class WifiNetworkSpecifier_build {
    private void test_WifiNetworkSpecifier_build() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            WifiNetworkSpecifier.Builder builder = new WifiNetworkSpecifier.Builder();
            builder.setSsidPattern(new PatternMatcher("TestNetwork", PatternMatcher.PATTERN_PREFIX));
            builder.setWpa2Passphrase("TestPassword");

            WifiNetworkSpecifier wifiNetworkSpecifier = builder.build();

            NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .removeCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .setNetworkSpecifier(wifiNetworkSpecifier)
                .build();

            final ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

            final ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(@NonNull Network network) {
                    // The network is available
                    super.onAvailable(network);
                    connectivityManager.bindProcessToNetwork(null);
                }
            };

            connectivityManager.requestNetwork(networkRequest, networkCallback);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                connectivityManager.unregisterNetworkCallback(networkCallback);
            }));
        } else {
            // WifiNetworkSpecifier is not supported prior to API 29
            Toast.makeText(this, "WifiNetworkSpecifier is not supported on this device.", Toast.LENGTH_SHORT).show();
        }
    }
}