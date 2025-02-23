import java.io.IOException;
import java.nio.file.*;

public class FileSystemProvider_newDirectoryStream {
    public void test_FileSystemProvider_newDirectoryStream() {
        // The directory we want to list
        Path directory = Paths.get("/some/directory/path/");

        // The file system provider 
        FileSystemProvider provider = FileSystems.getDefault().provider();

        try (DirectoryStream<Path> stream = provider.newDirectoryStream(directory, "*")) {
            for (Path file: stream) {
                System.out.println(file.getFileName());
            }
        } catch (IOException | DirectoryIteratorException x) {
            // IOException can never be thrown by the iteration.
            // In this snippet, it can // only be thrown by newDirectoryStream.
            System.err.println(x);
        }
    }
}