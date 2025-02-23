public class CursorLoader_loadInBackground {
    public void test_CursorLoader_loadInBackground(){
        Uri contentUri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[] { ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME };
        String selection = "((" + ContactsContract.Contacts.DISPLAY_NAME + " NOTNULL) AND (" 
                + ContactsContract.Contacts.DISPLAY_NAME + " != '' ))";
        String[] selectionArgs = null; 
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " ASC";

        CursorLoader cursorLoader = new CursorLoader(
            this,   // Parent activity context
            contentUri,   // Table to query
            projection,     // Projection to return
            selection,            // No selection clause
            selectionArgs,         // No selection arguments
            sortOrder             // Default sort order
        ); 

        cursorLoader.loadInBackground(); 
    }
}