public class SyncStateContract_getWithUri {

    public Uri test_SyncStateContract_getWithUri() {
        // Pre-required Constants
        String CONTENT_AUTHORITY = "com.example.app.provider";
        String ACCOUNT_NAME = "exampleAccountName";
        String ACCOUNT_TYPE = "exampleAccountType";

        // Building Sync Uri
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(ContentResolver.SCHEME_CONTENT);
        builder.authority(CONTENT_AUTHORITY);
        builder.appendPath(SyncStateContract.Columns.ACCOUNT_NAME);
        builder.appendPath(ACCOUNT_NAME);
        builder.appendPath(SyncStateContract.Columns.ACCOUNT_TYPE);
        builder.appendPath(ACCOUNT_TYPE);

        // Uri for Sync
        Uri uriSync = builder.build();

        // Return the created Uri
        return uriSync;
    }
}