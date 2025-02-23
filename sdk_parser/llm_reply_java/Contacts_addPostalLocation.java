public class Contacts_addPostalLocation {

    public void test_Contacts_addPostalLocation() {
        // ID of the contact to which we want to add the address
        long contactId = 1234;
        String newAddress = "123 Main St, Jeffersonville, IN 47130";

        Uri uri = ContactsContract.Data.CONTENT_URI;

        // Setup the values to be inserted
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, contactId);
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE);
        contentValues.put(ContactsContract.CommonDataKinds.StructuredPostal.STREET, newAddress);
        contentValues.put(ContactsContract.CommonDataKinds.StructuredPostal.TYPE, ContactsContract.CommonDataKinds.StructuredPostal.TYPE_HOME);

        try {
            getContentResolver().insert(uri, contentValues);
            Toast.makeText(this, "Postal address inserted successfully.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "There was a problem adding the Postal location.", Toast.LENGTH_SHORT).show();
            Log.e("Insert Postal Location", e.getMessage(), e);
        }
    }
}