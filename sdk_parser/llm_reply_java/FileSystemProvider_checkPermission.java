import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;

public class FileSystemProvider_checkPermission {
    public void test_FileSystemProvider_checkPermission() {
        // Path to the file that we want to check the permission
        Path path = Paths.get("/path/to/file"); 

        // Get the default FileSystemProvider
        FileSystemProvider provider = path.getFileSystem().provider();

        // check the read permission
        try {
            provider.checkAccess(path, java.nio.file.AccessMode.READ);
            // permission granted
            Toast.makeText(this, "Permission granted to read the file", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // permission denied or file not found
            Toast.makeText(this, "Permission denied or file not found", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}