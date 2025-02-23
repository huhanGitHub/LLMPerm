public class Contacts_getSetting {

    public void test_Contacts_getSetting() {
        // Initiate Cursor to read the system contact settings
        Cursor c = null;

        // Check the permission status
        if (checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {

            try {
                // URI for the contacts settings
                Uri uri = ContactsContract.Settings.CONTENT_URI;

                // Query the settings
                c = getContentResolver().query(uri, null, null, null, null);

                // Checking if the cursor returned any data
                if (c != null && c.moveToFirst()) {
                    // Displaying the first contact setting
                    Log.d("test_Contacts_getSetting", "Setting: " +
                            c.getString(c.getColumnIndex(ContactsContract.Settings.ANY_COLUMN_NAME)));
                }

            } catch (Exception e) {
                Log.d("test_Contacts_getSetting", "Error: " + e.getMessage());

            } finally {
                // Close the cursor if it's not already null
                if (c != null) {
                    c.close();
                }
            }

        } else {
            // Request the permission if not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS}, 1);
        }
    }

}