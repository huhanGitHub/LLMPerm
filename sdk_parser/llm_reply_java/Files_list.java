import android.util.Log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Files_list {
    public void test_Files_list() {
        try {
            // Path to directory
            Path dir = Paths.get(getFilesDir().getAbsolutePath());

            try (Stream<Path> paths = Files.list(dir)) {
                paths
                    .filter(Files::isRegularFile)
                    .forEach(path -> Log.i("Files list", path.getFileName().toString()));
            }

        } catch (IOException e) {
            Log.e("Files list", "An error occurred", e);
        }
    }
}