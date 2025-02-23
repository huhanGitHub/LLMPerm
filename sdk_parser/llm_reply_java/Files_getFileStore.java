import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Files_getFileStore {
    public void test_Files_getFileStore() {
        Path path = Paths.get(System.getProperty("user.home"));

        try {
            // Retrieve file store details
            FileStore store = Files.getFileStore(path);
            System.out.println("File store: " + store);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}