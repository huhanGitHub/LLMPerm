import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class Files_readSymbolicLink {
    public void test_Files_readSymbolicLink() {
        try {
            // Convert the normal string path to a symbolic link
            Path symbolicLinkPath = Paths.get("/path/to/your/symbolic/link");

            // Read the symbolic link
            Path realPath = Files.readSymbolicLink(symbolicLinkPath);

            // Displaying the actual path that the symbolic link refers to
            System.out.println(realPath.toString());
        } catch (IOException e) {
            // Exception handling code
            System.out.println("An error occurred while reading the symbolic link: " + e.getMessage());
        }
    }
}