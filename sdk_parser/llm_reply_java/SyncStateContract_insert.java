import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.accounts.Account;
import android.util.Log;
import android.app.Activity;

public class SyncStateContract_insert {
    public void test_SyncStateContract_insert(Activity activity, Account account, String authority, byte[] data) {
        try {
            final String CONTENT_URI = "content://com.android.contacts";
            final Uri uri = Uri.parse(CONTENT_URI);
            final ContentResolver resolver = activity.getContentResolver();

            // first, we need to check if there is already sync state data
            Cursor cursor = resolver.query(SyncStateContract.Helpers.getUri(uri, account, authority), null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                long rowId = cursor.getLong(cursor.getColumnIndex(SyncStateContract.Columns._ID));
                cursor.close();

                // update if there is already state data
                ContentValues values = new ContentValues();
                values.put(SyncStateContract.Columns.DATA, data);
                resolver.update(SyncStateContract.Helpers.getUri(uri, rowId), values, null, null);
            } else {
                // insert if there is no state data
                SyncStateContract.Helpers.insert(resolver, uri, account, authority, data);
            }
        } catch (Exception e) {
            Log.e("SyncStateContract", "Failed to insert/update", e);
        }
    }
}