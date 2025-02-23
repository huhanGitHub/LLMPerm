import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class Files_write {
    public void test_Files_write() {
        // Define the file path
        Path filePath = Paths.get("testFile.txt");

        // Define the content to write
        List<String> lines = Arrays.asList("This is line 1", "This is line 2");

        try {
            // Use Files.write to create the file and write the content into it
            Files.write(filePath, lines, StandardCharsets.UTF_8);

            System.out.println("File written successfully!");

        } catch (IOException e) {
            // Handle any exceptions
            System.out.println("An error occurred while writing the file.");
            e.printStackTrace();
        }
    }
}