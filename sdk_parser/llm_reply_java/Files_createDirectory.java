import android.os.Environment;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import android.util.Log;

public class Files_createDirectory {

    public void test_Files_createDirectory() {
        try {
            // Specify the path of the directory to be created
            String pathName = Environment.getExternalStorageDirectory() + "/ExampleDir"; //Note: You may need to request WRITE_EXTERNAL_STORAGE permission

            // Use Paths to get a Path representing the directory
            Path path = Paths.get(pathName);

            // Use Files.createDirectory() to create the directory
            if (!Files.exists(path)) {
                Files.createDirectory(path);
                Log.i("Directory Create Test", "Directory " + pathName + " has been created.");
            } else {
                Log.i("Directory Create Test", "Directory " + pathName
                        + " already exists.");
            }
        } catch (IOException e) {
            Log.e("Directory Create Test", "Error creating directory.", e);
        }
    }
}