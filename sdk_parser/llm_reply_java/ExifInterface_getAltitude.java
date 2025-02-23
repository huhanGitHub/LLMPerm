import android.media.ExifInterface;
import android.util.Log;
import java.io.IOException;

public class ExifInterface_getAltitude {
    public void test_ExifInterface_getAltitude() {
        try {
            // Specify the path to the photo
            String photoPath = "/sdcard/DCIM/Camera/IMG_20210512_000123.jpg";
            ExifInterface exifInterface = new ExifInterface(photoPath);

            // Get altitude data from EXIF information
            double altitude = exifInterface.getAltitude(0);

            // Output the altitude to the console. In real world app, you can use it as per your requirement instead of logging it to console.
            Log.d("Altitude", "Altitude: " + altitude);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}