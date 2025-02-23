public class Browser_sendString {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void test_Browser_sendString(Activity activity, Uri data, String string) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_HISTORY_BOOKMARKS)== PackageManager.PERMISSION_GRANTED) {
            try {
                ContentValues values = new ContentValues();
                values.put(Browser.BookmarkColumns.URL, data.toString());
                values.put(Browser.BookmarkColumns.TITLE, string);
                values.put(Browser.BookmarkColumns.BOOKMARK, 1);
                
                String[] proj = new String[] { Browser.BookmarkColumns._ID, Browser.BookmarkColumns.URL };
                String sel = Browser.BookmarkColumns.URL + " = ?";
                String[] selArgs = new String[] { data.toString() };
                Cursor cursor = activity.getContentResolver().query(Browser.BOOKMARKS_URI, proj, sel, selArgs, null);
                
                if (cursor != null) {
                    if (cursor.moveToFirst() && cursor.getInt(0) != 0) {
                        activity.getContentResolver().update(Browser.BOOKMARKS_URI, values, "_id=?", new String[]{cursor.getString(0)});
                    } else {
                        activity.getContentResolver().insert(Browser.BOOKMARKS_URI, values);
                    }

                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_HISTORY_BOOKMARKS}, 123);
        }
    }
}