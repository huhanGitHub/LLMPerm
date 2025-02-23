public class MediaCas_getServiceHidl {
    public void test_MediaCas_getServiceHidl() {
        // Assume we have a CAS system id
        int casSystemId = 0x1234;

        try {
            // Create a new MediaCas object with the specific CAS system id
            MediaCas mCas = new MediaCas(casSystemId);

            IMediaCasService mediaCasService = mCas.getService();

            MediaCas.PluginDescriptor desc = mediaCasService.getPluginDescriptor(casSystemId);

            Log.d("TEST", "CAS System ID: " + desc.getSystemId());
            Log.d("TEST", "CAS System Name: " + desc.getName());
            
        } catch (MediaCasException e) {
            Log.e("TEST", "MediaCasException: " + e.getMessage());
        }
    }
}