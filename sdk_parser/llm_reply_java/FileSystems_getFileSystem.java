import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Set;

public class FileSystems_getFileSystem {
    public void test_FileSystems_getFileSystem() {
        // Get the default FileSystem
        FileSystem fs = FileSystems.getDefault();

        // Get the FileStore attributes
        Iterable<FileStore> fsa = fs.getFileStores();
        for (FileStore fileStore : fsa) {
            System.out.println("File Store: " + fileStore);
        }

        // Get the set of the installed file type detectors
        Iterable<Path> rootDirectories = fs.getRootDirectories();
        for (Path path : rootDirectories) {
            System.out.println("Root Directory: " + path);
        }

        // Get the set of the available file attribute views
        Set<String> fileAttrViews = fs.supportedFileAttributeViews();
        for (String view : fileAttrViews) {
            System.out.println("Supported File Attribute View: " + view);
        }
    }
}