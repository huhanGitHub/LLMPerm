import java.io.File;
import java.io.IOException;
import android.util.Log;

public class File_setLastModified {
    public void test_File_setLastModified() {
        try {
            // Creating a new file
            File file = new File("/path/to/your/file");

            // Checking if the file exists
            if (!file.exists()) {
                boolean isCreated = file.createNewFile();
                if (!isCreated) {
                    Log.e("Test", "Failed to create new file");
                    return;
                }
            }

            // Get the current system time
            long currentTime = System.currentTimeMillis();

            // Set the last-modified time to the current system time
            boolean isModified = file.setLastModified(currentTime);

            // Checking if the last-modified time was successfully set
            if (!isModified) {
                Log.e("Test", "Failed to set the last-modified time");
            } else {
                Log.d("Test", "The last-modified time was set successfully");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}