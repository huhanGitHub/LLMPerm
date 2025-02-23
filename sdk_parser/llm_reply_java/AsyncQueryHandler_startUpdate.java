public class AsyncQueryHandler_startUpdate {
    public void test_AsyncQueryHandler_startUpdate() {
        // Assign the respective permission required for reading from the Contacts database.
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CONTACTS);

        // Check whether the permission is granted or not.
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            // If permission is granted, move on to perform the update operation.

            // Initialize the AsyncQueryHandler
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(getContentResolver()) {
                @Override
                protected void onUpdateComplete(int token, Object cookie, int result) {
                    // This method will be called when the update operation is complete
                    super.onUpdateComplete(token, cookie, result);
                    Log.i("info", "Update operation is complete");
                }
            };

            // Configure the operation parameters. Replace these with your actual parameters.
            Uri uri = ContactsContract.RawContacts.CONTENT_URI;
            String selection = ContactsContract.RawContacts.CONTACT_ID + "=?";
            String[] selectionArgs = new String[] {"contact_id_string"};
            ContentValues contentValues = new ContentValues();
            contentValues.put(ContactsContract.Data.DISPLAY_NAME, "new_name");

            // Perform the update operation.
            asyncQueryHandler.startUpdate(1, null, uri, contentValues, selection, selectionArgs);
        } else {
            // If permission is not granted, request the necessary permissions
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_CONTACTS},
                    PERMISSION_REQUEST_CODE);
        }
    }
}