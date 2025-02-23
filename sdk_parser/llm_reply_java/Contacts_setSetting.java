public class Contacts_setSetting {
    public void test_Contacts_setSetting() {
        // check if permission for contacts is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // if permission is not granted, request it
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS}, 1);
        } else {
            // since permission is granted, we can change settings
            try {
                // retrieve in writable mode
                Uri contactUri = ContactsContract.Contacts.CONTENT_URI;
                ContentValues contentValues = new ContentValues();
                // set Ringtone for a contact
                contentValues.put(ContactsContract.Contacts.CUSTOM_RINGTONE, "content://media/internal/audio/media/30"); // you should put actual url to the audio file here
                // here CONTACT_ID should be replaced with the actual id of the contact
                int update = getContentResolver().update(contactUri, contentValues, ContactsContract.Contacts._ID + " = ?", new String[]{"CONTACT_ID"});
                if (update > 0) {
                    Log.d("test", "Contact setting updated successfully");
                } else {
                    Log.d("test", "Update failed");
                }
            } catch (Exception e) {
                Log.d("test", "Error in setting contact setting", e);
            }
        }
    }
}