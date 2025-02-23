import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Browser;
import android.util.Log;

public class Browser_deleteFromHistory {
    public void test_Browser_deleteFromHistory() {
        String urlToBeDeleted = "https://www.example.com";

        ContentResolver resolver = getContentResolver();
        Cursor cursor = null;

        try {

            cursor = Browser.getAllBookmarks(resolver); //obtain all bookmarks
            cursor.moveToFirst(); // move to the first record

            while (!cursor.isAfterLast()) { // loop until the end
                String url = cursor.getString(cursor.getColumnIndex(Browser.BookmarkColumns.URL));
                if (url.equalsIgnoreCase(urlToBeDeleted)) { // if the url matches your criteria
                    String id = cursor.getString(cursor.getColumnIndex(Browser.BookmarkColumns._ID));
                    resolver.delete(Browser.BOOKMARKS_URI, Browser.BookmarkColumns._ID + "=?", new String[]{id});
                    break; // break the loop after deleting
                }
                cursor.moveToNext();
            }

        } catch (Exception e) {
            Log.e("Deleting URL", "Error deleting URL from history", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}