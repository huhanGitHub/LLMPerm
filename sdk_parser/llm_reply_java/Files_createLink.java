import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class Files_createLink {

    public void test_Files_createLink() {
        try {
            // Name of the existing file
            Path existingFilePath = Paths.get("/path/to/existingFile.txt");

            // Name of the new link
            Path linkPath = Paths.get("/path/to/newLinkFile.txt");

            // Create a link from new file to existing file
            Files.createLink(linkPath, existingFilePath);

            System.out.println("Link Created Successfully");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Error while creating link");
        }
    }
}