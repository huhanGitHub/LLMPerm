import java.io.File;
import android.util.Log;

public class File_canWrite {
    public boolean test_File_canWrite(String filePath) {
        // Creating a File instance
        File file = new File(filePath);
        // Checking the write permission
        boolean canWrite = file.canWrite();

        if (canWrite) {
            // Log or handle the case where you have the write permission
            Log.d("FilePermission", "You have write permission for this file.");
        } else {
            // Log or handle the case where you don't have the write permission
            Log.d("FilePermission", "You don't have write permission for this file.");
        }
        
        return canWrite;
    }
}