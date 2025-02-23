import android.app.usage.ExternalStorageStats;
import android.app.usage.StorageStatsManager;
import android.os.Process;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import androidx.annotation.RequiresApi;

public class ExternalStorageStats_getAudioBytes {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void test_ExternalStorageStats_getAudioBytes() {
        StorageStatsManager statsManager = (StorageStatsManager) getSystemService(Context.STORAGE_STATS_SERVICE);
        ExternalStorageStats externalStats = null;

        try {
            externalStats = statsManager.queryExternalStatsForUser(Process.myUserHandle());
        } catch (IOException e) {
            Log.e("Test", "Failed to get ExternalStorageStats", e);
            return;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("Test", "Failed to get ExternalStorageStats", e);
            return;
        }

        long audioBytes = externalStats.getAudioBytes();
        Log.i("Test", "Audio Bytes: " + audioBytes);
    }
}