public class IpConfiguration_setStaticIpConfiguration {
    public void test_IpConfiguration_setStaticIpConfiguration() {
        // Replace "192.168.1.5" with your static IP.
        InetAddress ip = InetAddresses.parseNumericAddress("192.168.1.5");

        // Replace 24 with your network prefix length. 24 is for 255.255.255.0.
        int prefixLength = 24;

        // Replace "192.168.1.1" with your gateway.
        InetAddress gateway = InetAddresses.parseNumericAddress("192.168.1.1");

        // Replace "8.8.8.8" with your DNS server. To use Google DNS, use 8.8.8.8.
        InetAddress dns = InetAddresses.parseNumericAddress("8.8.8.8");

        IpConfiguration ipConfiguration = new IpConfiguration();

        // For Static configuration set IpAssignment to STATIC.
        ipConfiguration.ipAssignment = IpAssignment.STATIC;

        // For Static IP configuration provide StaticIpConfiguration.
        ipConfiguration.staticIpConfiguration =
                new StaticIpConfiguration.Builder()
                        .setIpAddress(new LinkAddress(ip, prefixLength))
                        .setGateway(gateway)
                        .addDnsServer(dns)
                        .build();

        // Apply this configuration to the WiFi device. WiFi should be enabled.
        try {
            WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiConfiguration wifiConfiguration = new WifiConfiguration();

            // Replace "SSID" with your WiFi SSID and "password" with WiFi password.
            wifiConfiguration.SSID = String.format("\"%s\"", "SSID");
            wifiConfiguration.preSharedKey = String.format("\"%s\"", "password");

            // API level >= 21
            // Add the configuration to the Android device.
            // This just adds the configuration and then we need to enable it.
            int netId = wifiManager.addNetwork(wifiConfiguration);

            // Now enable the freshly added network configuration.
            wifiManager.enableNetwork(netId, true);

            // Reconnect with the new static IP.
            wifiManager.reconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}