import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class Files_readAttributes {
    public void test_Files_readAttributes() {
        Path filePath = Paths.get("/path/to/your/file");
        try {
            BasicFileAttributes attr = Files.readAttributes(filePath, BasicFileAttributes.class);

            System.out.println("File creation time: " + attr.creationTime());
            System.out.println("File was last accessed at: " + attr.lastAccessTime());
            System.out.println("File was last modified at: " + attr.lastModifiedTime());
            System.out.println("Is directory: " + attr.isDirectory());
            System.out.println("Is regular file: " + attr.isRegularFile());
            System.out.println("File size: " + attr.size());
        } catch (IOException e) {
            System.out.println("Error reading attributes: " + e.getMessage());
        }
    }
}