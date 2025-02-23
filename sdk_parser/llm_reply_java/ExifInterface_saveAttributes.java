import android.content.Context;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class ExifInterface_saveAttributes {

    public void test_ExifInterface_saveAttributes(Context context, Uri imageUri) {
        InputStream in = null;
        try {
            in = context.getContentResolver().openInputStream(imageUri);
            if (in == null) {
                Log.e("test_Exif", "Unable to open input stream for " + imageUri);
                return;
            }

            String pathToFile = imageUri.getPath();
            ExifInterface exifInterface = new ExifInterface(pathToFile); 
            exifInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE, "12.9715987");
            exifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, "77.5945627");

            // Save the changes
            exifInterface.saveAttributes();

            // Verify the changes
            String latitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            String longitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
            Log.d("test_Exif", "Latitude: " + latitude + "Longitude: " + longitude);
        } catch (IOException e) {
            Log.e("test_Exif", "Error processing exif data: " + e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e("test_Exif", "Error closing input stream: " + e.getMessage());
                }
            }
        }
    }
}