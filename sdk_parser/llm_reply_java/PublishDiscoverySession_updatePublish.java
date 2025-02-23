public class PublishDiscoverySession_updatePublish {
    public void test_PublishDiscoverySession_updatePublish() {
        try {
            // Create a new instance of the WifiAwareManager
            WifiAwareManager wifiAwareManager = 
                (WifiAwareManager) getSystemService(Context.WIFI_AWARE_SERVICE);

            if (wifiAwareManager == null) {
                Log.i("test", "WifiAwareManager not supported on device!");
                return;
            }

            // Create a session using the configuration object
            PublishConfig publishConfig = new PublishConfig.Builder()
                    .setServiceName("Test_Service")
                    .build();

            DiscoverySessionCallback publishDiscoverySessionCallback = new DiscoverySessionCallback() {
                @Override
                public void onPublishStarted(PublishDiscoverySession session) {
                    super.onPublishStarted(session);

                    // Once the session has started you can update the session
                    PublishConfig newPublishConfig = new PublishConfig.Builder()
                            .setServiceName("Upd_Test_Service")
                            .build();

                    session.updatePublish(newPublishConfig);  // updating the publish
                    Log.i("test", "Publish discovery session updated");
                }
            };

            wifiAwareManager.publish(publishConfig, publishDiscoverySessionCallback, new Handler(Looper.getMainLooper()));
        } catch (Exception e) {
            Log.e("test", "Exception in test_PublishDiscoverySession_updatePublish: " + e.getMessage());
        }
    }
}