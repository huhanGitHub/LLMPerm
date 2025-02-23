public class Browser_addSearchUrl {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void test_Browser_addSearchUrl() {
        // Check if the WRITE_HISTORY_BOOKMARKS permission is already available.
        if (checkSelfPermission(Manifest.permission.WRITE_HISTORY_BOOKMARKS)
                != PackageManager.PERMISSION_GRANTED) {
            // If not, then request permission
            requestPermissions(new String[]{Manifest.permission.WRITE_HISTORY_BOOKMARKS}, 0);
        } else {
            // If permission is already granted, then add a search URL in browser history
            try {
                String url = "https://www.example.com";
                String title = "Example Website";
                // Creating a values object to store the URL details
                ContentValues values = new ContentValues();
                values.put(Browser.BookmarkColumns.TITLE, title);
                values.put(Browser.BookmarkColumns.URL, url);
                values.put(Browser.BookmarkColumns.DATE, System.currentTimeMillis());
                // 0 = history, 1 = bookmark
                values.put(Browser.BookmarkColumns.BOOKMARK, 0);
                // Updating the browser history
                getContentResolver().insert(Browser.BOOKMARKS_URI, values);
                Log.d("Browser", "Search URL added successfully.");
            } catch (Exception e) {
                Log.e("Browser", "Error adding search URL.", e);
            }
        }
    }
}