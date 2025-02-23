public class InetAddress_getAllByName {
    public void test_InetAddress_getAllByName() {
        String hostName = "www.google.com"; // Specify the hostname you want to lookup
        try {
            // Get all IP addresses associated to the given hostname
            InetAddress[] addresses = InetAddress.getAllByName(hostName);
            for (InetAddress address : addresses) {
                Log.d("IP Address", address.getHostAddress()); //Log will print the IP addresses in Android Studio Logcat
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}