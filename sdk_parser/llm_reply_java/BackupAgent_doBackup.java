public class BackupAgent_doBackup {

    public void test_BackupAgent_doBackup() {
        try {
            MockBackupAgent backupAgent = new MockBackupAgent();
            ParcelFileDescriptor oldState = ParcelFileDescriptor.open(
                    new File(getFilesDir(), "oldState"), ParcelFileDescriptor.MODE_READ_WRITE);
            BackupDataOutput data = new BackupDataOutput(oldState.getFd());
            ParcelFileDescriptor newState = ParcelFileDescriptor.open(
                    new File(getFilesDir(), "newState"), ParcelFileDescriptor.MODE_READ_WRITE);

            backupAgent.onBackup(oldState, data, newState);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class MockBackupAgent extends BackupAgent {
        @Override
        public void onBackup(ParcelFileDescriptor oldState, BackupDataOutput data,
                             ParcelFileDescriptor newState) throws IOException {
            System.out.println("Backing up data...");
        }

        @Override
        public void onRestore(BackupDataInput data, int appVersionCode,
                              ParcelFileDescriptor newState) throws IOException {
            System.out.println("Restoring data...");
        }
    }
}