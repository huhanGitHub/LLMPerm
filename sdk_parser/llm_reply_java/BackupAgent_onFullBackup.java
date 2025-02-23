public class BackupAgent_onFullBackup {
    public void test_BackupAgent_onFullBackup() {
        MyBackupAgent myBackupAgent = new MyBackupAgent();
        FullBackupDataOutput output = new FullBackupDataOutput(ParcelFileDescriptor.autoCloseOutputStream(ParcelFileDescriptor.adoptFd(1)));

        try {
            myBackupAgent.onFullBackup(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class MyBackupAgent extends BackupAgent {
        @Override
        public void onBackup(ParcelFileDescriptor oldState, BackupDataOutput data, ParcelFileDescriptor newState) {}

        @Override
        public void onRestore(BackupDataInput data, int appVersionCode, ParcelFileDescriptor newState) {}

        @Override
        public void onFullBackup(FullBackupDataOutput data) throws IOException {
            FullBackup.backupToTar(getPackageName(), FullBackup.ROOT_TREE_TOKEN, null, 
                getApplicationInfo().dataDir, data.getData());

            FullBackup.backupToTar(getPackageName(), FullBackup.DATABASE_TREE_TOKEN, null, 
                getApplicationInfo().dataDir, data.getData());
        }
    }
}