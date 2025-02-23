import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class AsyncQueryHandler_startQuery extends AppCompatActivity {

    private AsyncQueryHandler mAsyncQueryHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        test_AsyncQueryHandler_startQuery();
    }

    private void test_AsyncQueryHandler_startQuery() {
        mAsyncQueryHandler = new AsyncQueryHandler(getContentResolver()) {

            @Override
            protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                        Log.d("TestTag", "Contact Name: " + displayName);
                    } while (cursor.moveToNext());
                }
                if (cursor != null) cursor.close();
            }
        };

        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        String[] projection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
        };

        if (checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            mAsyncQueryHandler.startQuery(
                    0, null, uri, projection, null, null, null);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},0);
        }
    }
}