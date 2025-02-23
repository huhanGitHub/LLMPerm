public class InetAddress_getByNameOnNet {
    public static void test_InetAddress_getByNameOnNet() {
        new AsyncTask<Void, Void, InetAddress>() {
            @Override
            protected InetAddress doInBackground(Void... params) {
                InetAddress address = null;
                try {
                    address = InetAddress.getByName("www.google.com");
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                return address;
            }

            @Override
            protected void onPostExecute(InetAddress inetAddress) {
                super.onPostExecute(inetAddress);
                if (inetAddress != null) {
                    Log.d("test_InetAddress", "IP Address: " + inetAddress.getHostAddress());
                }
            }
        }.execute();
    }
}