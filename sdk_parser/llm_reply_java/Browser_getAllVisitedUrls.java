import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.Browser;
import android.util.Log;
import androidx.annotation.RequiresApi;

public class Browser_getAllVisitedUrls {

    private static final int PERMISSIONS_REQUEST_READ_HISTORY_BOOKMARKS = 100;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void test_Browser_getAllVisitedUrls() {
        if (checkSelfPermission(Manifest.permission.READ_HISTORY_BOOKMARKS) == PackageManager.PERMISSION_GRANTED) {
            String[] proj = new String[]{Browser.BookmarkColumns.URL};
            String sel = Browser.BookmarkColumns.BOOKMARK + " = 0"; // 0 = history, 1 = bookmark
            Cursor mCur = getContentResolver().query(Browser.BOOKMARKS_URI, proj, sel, null, null);
            mCur.moveToFirst();

            if (mCur.moveToFirst() && mCur.getCount() > 0) {
                while (!mCur.isAfterLast()) {
                    Log.v("Visited URLs", mCur.getString(mCur.getColumnIndex(Browser.BookmarkColumns.URL)));
                    mCur.moveToNext();
                }
            }
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_HISTORY_BOOKMARKS}, PERMISSIONS_REQUEST_READ_HISTORY_BOOKMARKS);
        }
    }
}