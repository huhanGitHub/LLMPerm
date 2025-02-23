public class BackupAgent_doRestoreInternal {
    class TestBackupAgent extends BackupAgent {
        @Override
        public void onBackup(ParcelFileDescriptor oldState, BackupDataOutput data, ParcelFileDescriptor newState) throws IOException {

        }

        @Override
        public void onRestore(BackupDataInput data, int appVersionCode, ParcelFileDescriptor newState) throws IOException {

        }

        void doRestoreInternalTest() {
            ParcelFileDescriptor fileDescriptor = ParcelFileDescriptor.open(new File("path to your file"), ParcelFileDescriptor.MODE_READ_WRITE);
            BackupDataInput dataInput = new BackupDataInput(fileDescriptor.getFileDescriptor());

            try {
                this.onRestore(dataInput, 1, fileDescriptor);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}