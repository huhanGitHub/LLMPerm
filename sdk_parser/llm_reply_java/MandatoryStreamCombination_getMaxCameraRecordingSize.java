import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.MandatoryStreamCombination;
import android.util.Size;
import android.util.Log;

public class MandatoryStreamCombination_getMaxCameraRecordingSize {
    public void test_MandatoryStreamCombination_getMaxCameraRecordingSize() {
        CameraManager manager = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            String cameraId = manager.getCameraIdList()[0]; // getting the first back-facing camera
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            MandatoryStreamCombination combination = characteristics.get(CameraCharacteristics.SCALER_MANDATORY_STREAM_COMBINATIONS);
            if (combination != null) {
                Size[] sizes = combination.getOutputSizes(MediaRecorder.class); // use MediaRecorder class for video recording
                if (sizes != null && sizes.length > 0) {
                    Size maxSize = sizes[0]; // getting the first size which should be the largest one
                    for (int i = 1; i < sizes.length; i++) { // checking if there is any greater size available
                        if (sizes[i].getWidth() * sizes[i].getHeight() > maxSize.getWidth() * maxSize.getHeight()) {
                            maxSize = sizes[i];
                        }
                    }
                    Log.d("MaxCameraRecordingSize", "Width: " + maxSize.getWidth() + ", Height: " + maxSize.getHeight());
                }
            }
        } catch (CameraAccessException e) {
            // handle exception
            e.printStackTrace();
        }
    }
}