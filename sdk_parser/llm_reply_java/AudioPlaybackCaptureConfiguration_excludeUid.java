import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioPlaybackCaptureConfiguration;
import android.media.projection.MediaProjection;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

public class AudioPlaybackCaptureConfiguration_excludeUid {
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void test_AudioPlaybackCaptureConfiguration_excludeUid(MediaProjection mediaProjection, Integer uidToExclude, int REQUEST_CODE) {
        AudioPlaybackCaptureConfiguration configuration;
        try {
            if (checkSelfPermission(Manifest.permission.CAPTURE_AUDIO_OUTPUT) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                // Checks permissions and excludes a UID from audio capture configuration.
                configuration = new AudioPlaybackCaptureConfiguration
                        .Builder(mediaProjection)
                        .excludeUid(uidToExclude)
                        .build();

            } else {
                // If permissions are not provided, requests for necessary permissions.
                final String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAPTURE_AUDIO_OUTPUT};
                ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}