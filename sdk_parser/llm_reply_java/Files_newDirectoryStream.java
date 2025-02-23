import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Files_newDirectoryStream {
    private void test_Files_newDirectoryStream() throws IOException {
        // define the path
        Path dir = Paths.get("/path/to/directory");

        // use try-with-resources to ensure the stream is closed after usage
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path entry : stream) {
                // print or process each entry
                System.out.println(entry.getFileName());
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}