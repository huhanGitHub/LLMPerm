import android.content.Context;
import android.provider.DocumentsContract;
import android.net.Uri;
import android.database.Cursor;
import android.util.Log;
import android.Manifest;
import android.content.pm.PackageManager;

public class DocumentsContract_copyDocument {

    public void test_DocumentsContract_copyDocument(Context context, Uri srcDocUri, Uri destParentDocUri) {
        try {
            // Checking read/write permissions
            if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                throw new SecurityException("Lacking required permissions: READ_EXTERNAL_STORAGE and/or WRITE_EXTERNAL_STORAGE.");
            }
            // Copying the document
            Uri copiedDocumentUri = DocumentsContract.copyDocument(context.getContentResolver(), srcDocUri, destParentDocUri);
            Log.d("copyDocumentTest", "Copied document's new URI: " + copiedDocumentUri.toString());

            // Verifying the document copy
            // Opening a cursor for the copied document
            Cursor cursor = context.getContentResolver().query(copiedDocumentUri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                // Index of the column containing the Display Name of the document
                int displayNameIndex = cursor.getColumnIndex(DocumentsContract.Document.COLUMN_DISPLAY_NAME);
                if (displayNameIndex != -1) {
                    // Fetching the Display Name
                    String copiedDocDisplayName = cursor.getString(displayNameIndex);
                    Log.d("copyDocumentTest", "Copied document's DisplayName: " + copiedDocDisplayName);
                }
            }
            cursor.close(); // Always remember to close your cursors.
        } 
        catch (Exception e) {
            Log.e("copyDocumentTest", "An error occurred while copying the document.", e);
        }
    }
}