public class IdentityChangedListener_onIdentityChanged {
    private void test_IdentityChangedListener_onIdentityChanged() {
        // Get instance of WifiAwareManager
        WifiAwareManager wifiAwareManager = (WifiAwareManager) getSystemService(Context.WIFI_AWARE_SERVICE);

        // Ensure that Wi-Fi Aware is supported on the device and available
        if (wifiAwareManager!= null && wifiAwareManager.isAvailable()) {
            // Create Attach Callback
            AttachCallback attachCallback = new AttachCallback() {
                @Override
                public void onAttached(WifiAwareSession session) {
                    super.onAttached(session);
                    // The WifiAwareSession object session is valid and can be used to execute Wi-Fi Aware operations
                }

                @Override
                public void onAttachFailed() {
                    super.onAttachFailed();
                    // There's some error in attaching Wi-Fi Aware Session
                }
            };

            // Create IdentityChangedListener
            IdentityChangedListener identityChangedListener = new IdentityChangedListener() {
                @Override
                public void onIdentityChanged(byte[] mac) {
                    super.onIdentityChanged(mac);
                    // The MAC address of the device has changed. The new MAC address is contained in the mac variable
                }
            };

            // Attaching the current app to the Wi-Fi Aware service
            wifiAwareManager.attach(attachCallback, identityChangedListener, new Handler(Looper.getMainLooper()));
        } 
        else {
            // Wi-Fi Aware is not supported on this device
        }
    }
}