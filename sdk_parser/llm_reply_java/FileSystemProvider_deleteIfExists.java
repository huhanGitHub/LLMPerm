import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;

public class FileSystemProvider_deleteIfExists {
    public void test_FileSystemProvider_deleteIfExists() {
        // obtain a reference to the FileSystemProvider
        FileSystemProvider provider =  FileSystems.getDefault().provider();

        // create a path instance
        Path filePath = FileSystems.getDefault().getPath("path/to/file.txt");

        try {
            // use the provider to try and delete the file
            boolean isDeleted = provider.deleteIfExists(filePath);

            if (isDeleted) {
                System.out.println("File was deleted");
            } else {
                System.out.println("File doesn't exist or it couldn't be deleted");
            }
        } catch (IOException e) {
            // handle the exception
            System.out.println("An error occurred when trying to delete the file " + e.getMessage());
        }
    }
}