public class ContentProviderOperation_applyInternal {
    public void test_ContentProviderOperation_applyInternal() throws Exception {
        // Use Android's ContactsContract as our ContentProvider
        Uri uri = ContactsContract.RawContacts.CONTENT_URI;

        // Create a new contact entry
        ContentValues values = new ContentValues();
        values.put(ContactsContract.RawContacts.ACCOUNT_TYPE, "com.google");
        values.put(ContactsContract.RawContacts.ACCOUNT_NAME, "name@gmail.com");

        // Create an insert operation
        ContentProviderOperation operation = ContentProviderOperation.newInsert(uri)
                .withValues(values)
                .build();

        // Get a ContentResolver
        ContentResolver resolver = getContentResolver();

        // Apply the operation
        try {
            ContentProviderResult[] results = resolver.applyBatch(ContactsContract.AUTHORITY, 
                                                                  Collections.singletonList(operation));
            // Check the results
            if (results != null && results.length > 0) {
                Log.d("CPOTest", "New row ID: " + results[0].uri.getLastPathSegment());
            } else {
                Log.d("CPOTest", "ContentProviderOperation failed");
            }

        } catch (OperationApplicationException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}