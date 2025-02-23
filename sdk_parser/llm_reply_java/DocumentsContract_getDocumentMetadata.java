import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.DocumentsContract.Document;
import android.provider.OpenableColumns;
import android.util.Log;

public class DocumentsContract_getDocumentMetadata {

    public void test_DocumentsContract_getDocumentMetadata(Context context, Uri uri) {
        Cursor cursor = null;
        try {
            String[] projection = new String[] { Document.COLUMN_DISPLAY_NAME, Document.COLUMN_SIZE, Document.COLUMN_MIME_TYPE };
            cursor = context.getContentResolver().query(uri, projection, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                int mimeIndex = cursor.getColumnIndex(Document.COLUMN_MIME_TYPE);
                String name = cursor.getString(nameIndex);
                long size = cursor.getLong(sizeIndex);
                String mimeType = cursor.getString(mimeIndex);

                // Do something with the metadata...

                Log.d("Document Metadata", "Name: " + name + ", Size: " + size + ", MIME Type: " + mimeType);
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}