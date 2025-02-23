public class Network_createSocket {
    public void test_Network_createSocket() {
        final ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            Network[] networks = connectivityManager.getAllNetworks();
            for (Network network : networks) {
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
                // Here we choose a network that has internet (Usually, it should be your decision)
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI && networkInfo.isConnected()) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Socket socket = network.getSocketFactory().createSocket("example.com", 80);
                                if(socket.isConnected()) {
                                    Log.d("Network Test", "Socket successfully connected on network: " + network);
                                } else {
                                    Log.d("Network Test", "Socket connection failed on network: " + network);
                                }
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                    break;
                }
            } 
        }
    }
}