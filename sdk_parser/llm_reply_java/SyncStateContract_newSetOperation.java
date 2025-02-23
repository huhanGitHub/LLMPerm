public class SyncStateContract_newSetOperation {
    public void test_SyncStateContract_newSetOperation() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SyncStateContract.Constants.DATA, "test data");

        Uri uri = SyncStateContract.Helpers.getUriForAccount(null, "accountName");

        ContentProviderOperation operation = ContentProviderOperation.newInsert(uri)
                .withValues(contentValues)
                .build();

        ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
        operations.add(operation);

        ContentProviderResult[] result;
        try {
            result = getApplicationContext().getContentResolver().applyBatch(SyncStateContract.Constants.CONTENT_AUTHORITY, operations);
            for (ContentProviderResult record : result) {
                Log.d("Test", "New Record ID: " + record.uri);
            }
        } catch (RemoteException e) {
            Log.e("Test", "Error: " + e.getMessage());
        } catch (OperationApplicationException e) {
            Log.e("Test", "Error: " + e.getMessage());
        }
    }
}