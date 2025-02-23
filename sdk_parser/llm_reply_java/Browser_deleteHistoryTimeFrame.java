public class Browser_deleteHistoryTimeFrame {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public boolean test_Browser_deleteHistoryTimeFrame(Context context, long startMilliSec, long endMilliSec) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.WRITE_HISTORY_BOOKMARKS) == PackageManager.PERMISSION_GRANTED) {
                //Delete history
                String[] projection = new String[]{Browser.BookmarkColumns.DATE, Browser.BookmarkColumns.URL};
                String selection = Browser.BookmarkColumns.DATE + " BETWEEN ? AND ?";
                String[] selectionArgs = new String[]{String.valueOf(startMilliSec), String.valueOf(endMilliSec)};
                Cursor cursor = context.getContentResolver().query(Browser.BOOKMARKS_URI, projection, selection, selectionArgs, null);

                if(cursor != null) {
                    while (cursor.moveToNext()) {
                        String url = cursor.getString(cursor.getColumnIndex(Browser.BookmarkColumns.URL));
                        context.getContentResolver().delete(Browser.BOOKMARKS_URI,
                                                            Browser.BookmarkColumns.URL + "=?",
                                                            new String[] { url });
                    }

                    cursor.close();
                    return true;
                }

                return false;
            } else {
                throw new SecurityException("Requires WRITE_HISTORY_BOOKMARKS permission");
            }
        } else {
            //Below Android 6.0
            String[] projection = new String[]{Browser.BookmarkColumns.DATE, Browser.BookmarkColumns.URL};
            String selection = Browser.BookmarkColumns.DATE + " BETWEEN ? AND ?";
            String[] selectionArgs = new String[]{String.valueOf(startMilliSec), String.valueOf(endMilliSec)};
            Cursor cursor = context.getContentResolver().query(Browser.BOOKMARKS_URI, projection, selection, selectionArgs, null);

            if(cursor != null) {
                while (cursor.moveToNext()) {
                    String url = cursor.getString(cursor.getColumnIndex(Browser.BookmarkColumns.URL));
                    context.getContentResolver().delete(Browser.BOOKMARKS_URI,
                                                        Browser.BookmarkColumns.URL + "=?",
                                                        new String[] { url });
                }

                cursor.close();
                return true;
            }

            return false;
        }
    }
}