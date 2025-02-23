public class BackupAgent_onRestore extends BackupAgent {
    @Override
    public void onRestore(BackupDataInput data, int appVersionCode, ParcelFileDescriptor newState) throws IOException {
        // implement the logic to restore the data here
        // data: the data to restore
        // appVersionCode: the version code of the application
        // newState: a read/write file descriptor pointing to the restored data
    }
}