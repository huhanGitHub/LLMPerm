import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.util.Log;

public class DocumentsContract_getDocumentThumbnail {

    public void test_DocumentsContract_getDocumentThumbnail(Context context, Uri uri) {
        try {
            // Define thumbnail size
            final int THUMBNAIL_SIZE = 64;
            
            // Obtain Document ID
            String documentId = DocumentsContract.getDocumentId(uri);
            
            // Get bitmap thumbnail
            Bitmap thumbnail = DocumentsContract.getDocumentThumbnail(context.getContentResolver(), uri, 
                new android.graphics.Point(THUMBNAIL_SIZE, THUMBNAIL_SIZE), null);

            // Log the success
            if(thumbnail != null) {
                Log.i("test_DocumentThumbnail","Thumbnail Successfully Created");
            } else {
                Log.e("test_DocumentThumbnail","Failed to create thumbnail");
            }

        } catch (Exception e) {
            // Catch exceptions like FileNotFoundException
            throw new RuntimeException("Failed to create thumbnail", e);
        }
    }
}