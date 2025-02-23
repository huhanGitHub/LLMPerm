import android.Manifest;
import android.content.pm.PackageManager;
import android.database.AbstractCursor;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AbstractCursor_setNotificationUris extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private Uri someOtherUri = Uri.parse("your-uri-path");
    private static final String TAG = "AbstractCursor_test";

    private void test_AbstractCursor_setNotificationUris() {
        // check if we have permission to read the user's gallery
        int hasReadPermission = ActivityCompat.checkSelfPermission(this, 
            Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {
            // if not, ask for it
            ActivityCompat.requestPermissions(this, 
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            return;
        }

        // get the files from the gallery
        Uri galleryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = getContentResolver().query(galleryUri, null, null, null, null);

        if (cursor instanceof AbstractCursor) {
            AbstractCursor abstractCursor = (AbstractCursor) cursor;
            // check notifications at galleryUri and someOtherUri
            abstractCursor.setNotificationUris(getContentResolver(),
                    new ArrayList<>(Arrays.asList(galleryUri, someOtherUri)));

            // check if the notifications have been properly set
            List<Uri> uris = abstractCursor.getNotificationUris();
            if (uris.contains(galleryUri) && uris.contains(someOtherUri)) {
                Log.d(TAG, "URIs properly registered.");
            } else {
                Log.d(TAG, "Failed to register URIs.");
            }
        }
    }    
}