import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;
import java.io.IOException;
import java.nio.file.FileStore;

public class FileSystemProvider_getFileStore {
    public void test_FileSystemProvider_getFileStore() {
        try {
            // Get the default file system
            FileSystemProvider provider = FileSystems.getDefault().provider();
            
            // Get the path where the file system object is stored
            Path path = FileSystems.getDefault().getPath("/");

            // Retrieve the file store
            FileStore store = provider.getFileStore(path);
            
            // Print the file store
            System.out.println("File Store: " + store.toString());
        } catch (IOException e) {
            // Handle any IO exceptions
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}