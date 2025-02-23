import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class FileOwnerAttributeView_getOwner {
    public void test_FileOwnerAttributeView_getOwner() {
        // Create a path object representing the file whose owner we want to retrieve
        Path path = Paths.get("/path/to/your/file");

        try {
            // Instantiate a FileOwnerAttributeView object for the file at the given path
            FileOwnerAttributeView fileOwnerAttributeView = Files.getFileAttributeView(path, FileOwnerAttributeView.class);

            // Get the owner of the file
            UserPrincipal ownerPrincipal = fileOwnerAttributeView.getOwner();

            // Print out the name of the owner
            String ownerName = ownerPrincipal.getName();
            System.out.println("Owner of the file: " + ownerName);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}