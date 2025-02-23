import android.media.tv.interactive.TvInteractiveAppManager;
import android.os.Build;
import android.widget.Toast;
import androidx.annotation.RequiresApi;

public class TvInteractiveAppManager_onRequestStartRecording {

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void test_TvInteractiveAppManager_onRequestStartRecording() {
        // Creating an instance of the TvInteractiveAppManager
        TvInteractiveAppManager tvInteractiveAppManager = TvInteractiveAppManager.getInstance(this);

        // Start the Recording
        TvInteractiveAppManager.RecordingRequest recordingRequest = new TvInteractiveAppManager.RecordingRequest(
                "content://media/external/tv/program/1",
                "Recording Test",
                "Test Description",
                System.currentTimeMillis(),
                3600000); // For recording duration, 3600000 milliseconds = 1 hour

        try {
            tvInteractiveAppManager.onRequestStartRecording(recordingRequest);
            Toast.makeText(this, "Recording started successfully.", Toast.LENGTH_SHORT).show();

            // Wait for a few seconds
            Thread.sleep(3000);

            // Check if the recording is still running
            if (tvInteractiveAppManager.isRecordingInProgress()) {
                Toast.makeText(this, "Recording is in progress.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Recording is not in progress.", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error starting recording: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}