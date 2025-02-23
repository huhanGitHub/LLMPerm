public class ExternalStorageStats_getTotalBytes {

    @TargetApi(Build.VERSION_CODES.O)
    private void test_ExternalStorageStats_getTotalBytes() {
        // Get the app's package name
        String myPackageName = getPackageName();

        // Get the storage manager
        StorageStatsManager storageStatsManager = (StorageStatsManager) getSystemService(Context.STORAGE_STATS_SERVICE);

        // Get the storage volume 
        StorageManager storageManager = (StorageManager) getSystemService(Context.STORAGE_SERVICE);
        List<StorageVolume> storageVolumes =  storageManager.getStorageVolumes();
        UUID appSpecificUUID = null;
        for(StorageVolume volume : storageVolumes){
            if(volume.isPrimary()){
                appSpecificUUID = volume.getUuid();
                break;
            }
        }

        // Get external storage stats
        ExternalStorageStats externalStorageStats = null;
        try {
            externalStorageStats = storageStatsManager.queryExternalStatsForUser(appSpecificUUID, Process.myUserHandle());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get total bytes
        long totalBytes = externalStorageStats.getTotalBytes();

        // Logging total bytes
        Log.d("MainActivity", "Total Bytes: " + totalBytes );
    }
}