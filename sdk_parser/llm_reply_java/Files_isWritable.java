import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Files_isWritable {
    public boolean test_Files_isWritable() {
        // Define your file directory here. 
        Path path = Paths.get("/path/to/your/file");

        boolean isWritable = false;

        try {
            // Check if the file path is writable
            isWritable = Files.isWritable(path);
        } catch (SecurityException e) {
            // Handle exception if you do not have the permissions to access the file.
            e.printStackTrace();
        }

        return isWritable;
    }
}