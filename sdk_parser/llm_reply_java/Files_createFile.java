import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Files_createFile {
    
    public void test_Files_createFile() {
        Path pathToFile = Paths.get("/tmp/my-file.txt"); // Replace the path string with your real path

        try {
            // Creates a new and empty file, if it doesn't exist at the given location
            Files.createFile(pathToFile);

            // Let's try to write a simple message into it
            String message = "Hello, World!";
            Files.write(pathToFile, message.getBytes());

            System.out.println("File successfully created at " + pathToFile.toString());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to create a file due to " + e.getMessage());
        }
    }
}