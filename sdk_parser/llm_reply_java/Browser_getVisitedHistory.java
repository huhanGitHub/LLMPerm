public class Browser_getVisitedHistory {

    private static final int MY_PERMISSIONS_REQUEST_READ_HISTORY_BOOKMARKS = 99; // Use a unique request code

    public void test_Browser_getVisitedHistory() {
        // Check whether the app has permission to read browser's history
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.READ_HISTORY_BOOKMARKS)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_HISTORY_BOOKMARKS},
                        MY_PERMISSIONS_REQUEST_READ_HISTORY_BOOKMARKS);

        } else {
        
            // Get the Browser's history
            String[] projection = new String[] {
                Browser.BookmarkColumns.TITLE, 
                Browser.BookmarkColumns.URL
            };

            String selection = Browser.BookmarkColumns.BOOKMARK + " = 0"; // 0 = history item, 1 = bookmark item

            Cursor cursor = getContentResolver().query(Browser.BOOKMARKS_URI, projection, selection, null, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String bookmarkTitle = cursor.getString(cursor.getColumnIndex(Browser.BookmarkColumns.TITLE));
                    String url = cursor.getString(cursor.getColumnIndex(Browser.BookmarkColumns.URL));

                    // Do something with the history item...
                }
                
                cursor.close();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_HISTORY_BOOKMARKS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // If permission granted, get the browser history
                    test_Browser_getVisitedHistory();
                } else {
                    // Permission denied. Disabling the functionality that depends on this permission.
                    // Let the user know the feature is disabled due to the denial of permission.
                }
                return;
            }
        }
    }
}