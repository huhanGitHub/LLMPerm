import java.nio.file.*;
import java.io.IOException;

public class SecureDirectoryStream_newDirectoryStream {

    public void test_SecureDirectoryStream_newDirectoryStream() {
        Path dir = Paths.get("YourDirectoryPath");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path path : stream) {
                System.out.println(path.getFileName());
            }
        } catch (IOException ex) {
            System.err.println("Error occurred while reading the directory: " + ex.getMessage());
        }
    }
}