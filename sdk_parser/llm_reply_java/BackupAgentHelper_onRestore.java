public class BackupAgentHelper_onRestore {
    public void test_BackupAgentHelper_onRestore(Context context) {
        int permissionCheck = ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            BackupAgentHelper backupAgentHelper = new MyBackupAgent();

            BackupDataInput data = null; // data to be restored
            int appVersionCode = 0; // version number of your app
            ParcelFileDescriptor newState = null; // file descriptor for the new state file 

            try {
                backupAgentHelper.onRestore(data, appVersionCode, newState);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            // Show rationale and request permission.
        }
    }

    public class MyBackupAgent extends BackupAgentHelper {
        // The name of the SharedPreferences file
        static final String PREFS = "user_preferences";

        // A key to uniquely identify the set of backup data
        static final String PREFS_BACKUP_KEY = "prefs";

        // Allocate a helper and add it to the backup agent
        @Override
        public void onCreate() {
            SharedPreferencesBackupHelper helper = new SharedPreferencesBackupHelper(this, PREFS);
            addHelper(PREFS_BACKUP_KEY, helper);
        }
    }
}