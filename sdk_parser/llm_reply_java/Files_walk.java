import android.os.Environment;
import android.util.Log;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Files_walk {
    public void test_Files_walk() {
        String dirPath = Environment.getExternalStorageDirectory().toString();
        try (Stream<Path> stream = Files.walk(Paths.get(dirPath), 1)) {
            stream.filter(Files::isReadable)
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        Log.i("File Path", String.valueOf(path));
                    });
        } catch (IOException e) {
            Log.e("Files Walk Error", "Unable to read directory", e);
        }
    }
}