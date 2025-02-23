import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class Files_createDirectories {
    public void test_Files_createDirectories() {
        // Define the path to the directories
        Path pathToDirectories = Paths.get("/path/to/directories");

        try {
            // Use Files.createDirectories() function to create the directories
            Files.createDirectories(pathToDirectories);

            // Print a success message
            System.out.println("Directories were created successfully.");
        } catch (IOException e) {
            // Print an error message if there is any problem while creating the directories
            System.err.println("An error occurred while trying to create directories: " + e.getMessage());
        }
    }
}