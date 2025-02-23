public class BackupAgent_fullBackupFile extends BackupAgent {
    @Override
    public void onBackup(ParcelFileDescriptor oldState, BackupDataOutput data, ParcelFileDescriptor newState) throws IOException {
        // Not relevant for this example
    }

    @Override
    public void onRestore(BackupDataInput data, int appVersionCode, ParcelFileDescriptor newState) throws IOException {
        // Not relevant for this example
    }

    public void test_BackupAgent_fullBackupFile() {
        // Check External Storage permission
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Check BACKUP permission
            if(ContextCompat.checkSelfPermission(this, "android.permission.BACKUP") == PackageManager.PERMISSION_GRANTED) {
                // Create file for backup
                File backupFile = new File(Environment.getExternalStorageDirectory(), "backup_test");
                try {
                    if(!backupFile.createNewFile()) {
                        Log.d("Backup Test", "File already exists or failed to create");
                    }
                    fullBackupFile(backupFile, new FullBackupDataOutput(new ParcelFileDescriptor.AutoCloseOutputStream(
                        ParcelFileDescriptor.open(new File(Environment.getExternalStorageDirectory(), "backup_test"),
                        ParcelFileDescriptor.MODE_READ_WRITE))));
                } catch(IOException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("Backup Test", "Backup permission not granted");
            }
        } else {
            Log.d("Backup Test", "Write External Storage permission not granted");
        }
    }
}