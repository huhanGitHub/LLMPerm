import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;

public class FileSystemProvider_newInputStream {

    public void test_FileSystemProvider_newInputStream(String filePath) throws IOException {
        // Specify the file we want to read from
        FileSystem fileSystem = FileSystems.getDefault();
        Path path = fileSystem.getPath(filePath);

        // Get the provider of the FileSystem
        FileSystemProvider provider = fileSystem.provider();

        // Open a new InputStream
        try (InputStream in = provider.newInputStream(path)) {
            // Read from the file
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                // Do something with each line from the file
                System.out.println(line);
            }
        } catch (IOException e) {
            // Handle the exception
            throw new RuntimeException("Could not open file", e);
        }
    }
}