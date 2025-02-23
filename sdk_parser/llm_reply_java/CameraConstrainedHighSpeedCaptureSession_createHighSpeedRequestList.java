public class CameraConstrainedHighSpeedCaptureSession_createHighSpeedRequestList {

    void test_CameraConstrainedHighSpeedCaptureSession_createHighSpeedRequestList() {
        try {
            // Assume that we have an instance of the CameraConstrainedHighSpeedCaptureSession
            CameraConstrainedHighSpeedCaptureSession highSpeedSession;

            // Assume that we have a CaptureRequest.Builder object already
            CaptureRequest.Builder builder;

            // Add necessary configuration to the builder then build
            CaptureRequest request = builder.build();

            // Call to function we want to test
            List<CaptureRequest> highSpeedRequestList =
                    highSpeedSession.createHighSpeedRequestList(request);

            // When permissions or configurations are not correctly done; 
            // the function can throw a CameraAccessException
        } catch (CameraAccessException e) {
            // Handle the exception appropriately
            e.printStackTrace();
        }
    }
}