public class Browser_getAllBookmarks {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void test_Browser_getAllBookmarks() {
        ContentResolver cr = getContentResolver();
        String[] projection = new String[] {Browser.BookmarkColumns.TITLE,
                                            Browser.BookmarkColumns.URL};
        String selection = Browser.BookmarkColumns.BOOKMARK + " = 0"; // 0 = history, 1 = bookmark
        Cursor cursor = cr.query(Browser.BOOKMARKS_URI, projection, selection, null, null);
        if (cursor != null) {
            while(cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndex(Browser.BookmarkColumns.TITLE));
                String url = cursor.getString(cursor.getColumnIndex(Browser.BookmarkColumns.URL));
                // Do something with title and url
                Log.i("Browser Details", "Title: " + title + ", URL: " + url);
            }
            cursor.close();
        }
    }
}