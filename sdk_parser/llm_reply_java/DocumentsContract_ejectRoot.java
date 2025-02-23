import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.util.Log;

public class DocumentsContract_ejectRoot {
    private static final String TAG = "DocumentsContract_ejectRoot";
    private static final String ROOT_URI = "content://com.android.externalstorage.documents/tree/primary:";

    public void test_DocumentsContract_ejectRoot(Context context) {
        try {
            Uri uri = Uri.parse(ROOT_URI);
            ContentResolver contentResolver = context.getContentResolver();

            boolean ejected = DocumentsContract.ejectRoot(contentResolver, uri);

            if (ejected) {
                Log.i(TAG, "Successfully ejected.");
            } else {
                Log.i(TAG, "Failed to eject.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Test failed: " + e.getMessage());
        }
    }
}