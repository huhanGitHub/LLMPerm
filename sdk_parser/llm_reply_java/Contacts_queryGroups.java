public class Contacts_queryGroups {

    public void test_Contacts_queryGroups() {
        // Check if we have the permission to read contacts.
        if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // If not, request the permission
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_READ_CONTACTS);
            return;
        }

        // Android data content URI (contacts)
        Uri uri = ContactsContract.Groups.CONTENT_URI;

        // The columns we are interested in.
        String[] projection = new String[]{
                ContactsContract.Groups._ID,
                ContactsContract.Groups.TITLE
        };

        // Query the table
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        // Validate cursor
        if (cursor == null) {
            Log.d("test_Contacts_queryGroups", "Error querying Contacts. Cursor is null.");
            return;
        }

        // Loop over the cursor
        while (cursor.moveToNext()) {
            // Get the fields we need
            long id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Groups._ID));
            String title = cursor.getString(cursor.getColumnIndex(ContactsContract.Groups.TITLE));

            // Do something with the data
            Log.d("test_Contacts_queryGroups", "Group Id: " + id + ", Title: " + title);
        }

        cursor.close();
    }
}