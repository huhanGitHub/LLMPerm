import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import android.util.Log;

public class Files_move {

    public void test_Files_move() {
        try {
            Path source = Paths.get("/path/to/original/file.txt");
            Path target = Paths.get("/path/to/new/location/file.txt");
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
            Log.i("Test", "File moved successfully.");
        } catch (Exception ex) {
            Log.e("Test", "An error occurred while moving the file: " + ex.getMessage());
        }
    }
}