public class SyncStateContract_set {
    public void test_SyncStateContract_set() {
        try {
            // get instance of Account
            Account account = new Account("testAccount", "com.test.package");
            String authority = "com.test.provider";

            // construct URI with account and authority
            Uri uri = SyncStateContract.Helpers.getUriForAccount(account, authority);

            byte[] data = new byte[] {1, 2, 3, 4, 5}; // the data you want to set

            // get the ContentResolver and set the sync state
            ContentResolver resolver = this.getContentResolver();
            SyncStateContract.Helpers.set(resolver, uri, data);

        } catch (Exception exception) {
            Log.e("SyncStateContractTest", "Failed to test SyncStateContract: "+exception.getMessage());
        }
    }
}