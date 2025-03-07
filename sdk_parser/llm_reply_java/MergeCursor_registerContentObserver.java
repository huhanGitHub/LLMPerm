public class MergeCursor_registerContentObserver {

    public void test_MergeCursor_registerContentObserver() {

        // Create cursor array
        Cursor[] cursors = new Cursor[2];

        // Cursor1: Query from a database
        SQLiteDatabase db1 = getApplicationContext().openOrCreateDatabase("DB1", Context.MODE_PRIVATE, null);
        Cursor cursor1 = db1.query("table1", null, null, null, null, null, null);
        cursors[0] = cursor1;

        // Cursor2: Query from another database
        SQLiteDatabase db2 = getApplicationContext().openOrCreateDatabase("DB2", Context.MODE_PRIVATE, null);
        Cursor cursor2 = db2.query("table2", null, null, null, null, null, null);
        cursors[1] = cursor2;

        // MergeCursor
        MergeCursor mergeCursor = new MergeCursor(cursors);

        // Create a ContentObserver
        ContentObserver contentObserver = new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);

                // This method is called when a content change occurs.
                // e.g. Handling data update
                Log.d("MergeCursor Test", "Content has changed");
            }
        };

        // Register ContentObserver to MergeCursor
        mergeCursor.registerContentObserver(contentObserver);

        // Start observing
        getContentResolver().registerContentObserver(ContactsContract.Contacts.CONTENT_URI, true, contentObserver);

        // Doing something...
        // ...

        // After all cursor actions performed, don't forget to close them 
        mergeCursor.close();
        cursor1.close();
        cursor2.close();
        db1.close();
        db2.close();
    }
}