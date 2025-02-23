public class Contacts_addToMyContactsGroup {
    private void test_Contacts_addToMyContactsGroup() {
        // Request required permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS}, 1);
        } else {
            addContactToGroup();
        }
    }

    private void addContactToGroup() {
        long rawContactId = 0;  // you need to fetch the desired RawContactId here
        long groupId = 0; // you need to fetch the desired GroupId here

        ContentValues values = new ContentValues();
        values.put(ContactsContract.CommonDataKinds.GroupMembership.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID, groupId);
        values.put(ContactsContract.CommonDataKinds.GroupMembership.MIMETYPE, ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE);
        getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    addContactToGroup();
                } else {
                    Toast.makeText(this, "Permission denied for write contacts!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}