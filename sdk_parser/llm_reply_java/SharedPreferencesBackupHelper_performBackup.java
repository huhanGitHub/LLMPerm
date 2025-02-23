public class SharedPreferencesBackupHelper_performBackup {

    private void test_SharedPreferencesBackupHelper_performBackup() throws IOException {
        // Create a new SharedPreferencesBackupHelper
        BackupAgentHelper backupAgent = new BackupAgentHelper();
        SharedPreferencesBackupHelper helper = new SharedPreferencesBackupHelper(this, PREFS_NAME);
        // Add the helper to the BackupAgent
        backupAgent.addHelper(PREFS_NAME, helper);
        // Create dummy data and output
        BackupDataOutput data = new BackupDataOutput(ParcelFileDescriptor.adoptFd(1).getFileDescriptor());
        // Perform Backup
        helper.performBackup(new ParcelFileDescriptor(ParcelFileDescriptor.adoptFd(1)), data, BackupAgentHelper.TYPE_FULL);
    }
}