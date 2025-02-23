import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;
import java.util.List;

public class AudioPlaybackCaptureConfiguration_addMatchingUid {
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void test_AudioPlaybackCaptureConfiguration_addMatchingUid(Context context) {
        // Starts with an audio recording source definition
        MediaProjection mMediaProjection = ((MediaProjectionManager) context.getSystemService(Context.MEDIA_PROJECTION_SERVICE)).getMediaProjection(Activity.RESULT_OK, new Intent());
        AudioPlaybackCaptureConfiguration originalConfig = mMediaProjection.createAudioPlaybackCaptureConfiguration();
        
        // Gets list of packages that produce audio for the above config
        List<AudioPlaybackCaptureConfiguration.AppCapturePolicy> originalPolicyList = originalConfig.getMatchingPackages();

        // Adding a matching UID of another audio producing app
        try {
            int uid = context.getPackageManager().getPackageUid("com.example.otherapp", 0);
            AudioPlaybackCaptureConfiguration newConfig = originalConfig.addMatchingUid(uid);

            // Verifying if the addition was successful
            List<AudioPlaybackCaptureConfiguration.AppCapturePolicy> newPolicyList = newConfig.getMatchingPackages();
            boolean isAdditionSuccessful = false;

            for (AudioPlaybackCaptureConfiguration.AppCapturePolicy appCapturePolicy : newPolicyList) {
                if (appCapturePolicy.uid == uid) {
                    isAdditionSuccessful = true;
                    break;
                }
            }

            if (isAdditionSuccessful)
                Log.i("AudioCaptureTest", "The new UID is successfully added!");
            else
                Log.i("AudioCaptureTest", "Failed to add the new UID!");

        } catch (PackageManager.NameNotFoundException e) {
            Log.e("AudioCaptureTest", "Error: Package not found", e);
        }
    }
}