public class ExternalStorageStats_getAppBytes {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public long test_ExternalStorageStats_getAppBytes() {
        StorageStatsManager storageStatsManager = (StorageStatsManager) this.getSystemService(Context.STORAGE_STATS_SERVICE);
        try {
            // Retrieving storage stats for our application
            // package name can be retrieved by getPackageName() in context of Activity
            ExternalStorageStats externalStorageStats = 
                      storageStatsManager.queryExternalStatsForUser(Process.myUserHandle(), getPackageName());

            // Retrieving amount of bytes used by the app
            return externalStorageStats.getAppBytes();
        } catch (IOException | PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }
}