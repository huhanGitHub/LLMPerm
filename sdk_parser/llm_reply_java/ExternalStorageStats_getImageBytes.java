import android.app.usage.ExternalStorageStats;
import android.app.usage.StorageStatsManager;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

public class ExternalStorageStats_getImageBytes {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void test_ExternalStorageStats_getImageBytes(String packageName) {
        try {
            ApplicationInfo applicationInfo = this.getPackageManager().getApplicationInfo(packageName, 0);
            StorageStatsManager storageStatsManager = (StorageStatsManager) getSystemService(Context.STORAGE_STATS_SERVICE);
            ExternalStorageStats externalStorageStats = storageStatsManager.queryExternalStatsForUser(applicationInfo.storageUuid, android.os.Process.myUserHandle());
            long imageBytes = externalStorageStats.getImageBytes();
            Log.d("AppLog", "External Image Bytes for package:" + packageName + " is:" + imageBytes);
        } catch (PackageManager.NameNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}