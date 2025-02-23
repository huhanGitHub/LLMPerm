public class CursorWrapper_registerContentObserver {

    public void test_CursorWrapper_registerContentObserver() {
        //To get content resolver, this should be done inside an activity or fragment
        ContentResolver contentResolver = getContentResolver();

        //Creating dummy Uri for sample
        Uri uri = Uri.parse("content://your.authority/path");

        //Query the content provider
        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        //Creating the cursor wrapper
        CursorWrapper cursorWrapper = new CursorWrapper(cursor);

        //Creating the content observer
        ContentObserver contentObserver = new ContentObserver(new Handler(Looper.getMainLooper())) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                //Do something in response to observed content change
                //It will be called whenever data at the given URI changes
            }
        };

        //Registering the content observer on cursor wrapper
        cursorWrapper.registerContentObserver(contentObserver);

        //Do something with the wrapped cursor
        //Don't forget to close it after finishing
        cursorWrapper.close();
    }

}