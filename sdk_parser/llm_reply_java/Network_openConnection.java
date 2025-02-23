public class Network_openConnection {

    public void test_Network_openConnection() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                ConnectivityManager connMgr = 
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                Network[] networks = connMgr.getAllNetworks();
                for (Network network : networks) {
                    try {
                        NetworkCapabilities networkCapabilities = connMgr.getNetworkCapabilities(network);
                        if (networkCapabilities != null && 
                           (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))) {
                            URL url = new URL("http://example.com");
                            HttpURLConnection conn = (HttpURLConnection) network.openConnection(url);
                            if (conn.getResponseCode() == 200) {
                                Log.d(TAG, "Network openConnection Test Successful");
                            }
                            conn.disconnect();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Network openConnection Test Failed", e);
                    }
                }
                return null;
            }
        }.execute();
    }
}