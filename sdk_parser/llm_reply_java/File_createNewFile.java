import java.io.File;
import java.io.IOException;
import android.util.Log;

public class File_createNewFile {
    private void test_File_createNewFile() {
        //creating a new file instance
        File file = new File("/path/to/file.txt");

        //checks if the file exists
        if (!file.exists()) {
            try {
                //trying to create a new file
                if (file.createNewFile()) {
                    Log.d("File Operations", "File created : " + file);
                } else {
                    Log.d("File Operations", "File not created");
                }
            } catch (IOException e) {
                Log.d("File Operations", "Error while creating file " + e.getMessage());
            }
        } else {
            Log.d("File Operations", "File already exists: " + file);
        }
    }
}