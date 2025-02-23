public class BackupAgent_onRestoreFile {
    public void test_BackupAgent_onRestoreFile() {
        
        try {
            ParcelFileDescriptor oldState = null;
            int token = 0;
            String packageName = "com.example.myapp";
            FullBackupDataOutput newState = null;

            BackupAgent backupAgent = new BackupAgent() {
                @Override
                public void onCreate() {

                }

                @Override
                public void onDestroy() {

                }
     
                @Override
                public void onBackup(ParcelFileDescriptor oldState, BackupDataOutput data, ParcelFileDescriptor newState) throws IOException {

                }

                @Override
                public void onRestore(BackupDataInput data, int appVersionCode, ParcelFileDescriptor newState) throws IOException {
                
                }
            };

            backupAgent.onRestoreFile(null, 0, null, 0, 0, null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}