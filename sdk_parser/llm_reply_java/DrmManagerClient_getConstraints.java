public class DrmManagerClient_getConstraints {
    public void test_DrmManagerClient_getConstraints() {
        // Get application context
        Context context = getApplicationContext();

        // Initialize the DrmManagerClient
        DrmManagerClient drmManagerClient = new DrmManagerClient(context);

        // Build a DRM info request
        DrmInfoRequest drmInfoRequest = new DrmInfoRequest(DrmInfoRequest.TYPE_REGISTRATION_INFO, "application/ogg");

        // Get the constraints
        try {
            drmManagerClient.getConstraints(drmInfoRequest, DrmManagerClient.ACTION_PLAY);
        } catch (Exception e) {
            Log.e("test_DrmManagerClient_getConstraints", "Exception occurred while getting constraints: ", e);
        }
    }
}