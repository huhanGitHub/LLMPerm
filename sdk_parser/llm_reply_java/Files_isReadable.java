import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Files_isReadable {
    public void test_Files_isReadable() throws Exception {
        // Define the path of the file
        Path filePath = Paths.get("/path/to/your/file");

        // Check if the file is readable
        boolean isReadable = Files.isReadable(filePath);

        // Print the result
        System.out.println("Is file readable: " + isReadable);
    }
}