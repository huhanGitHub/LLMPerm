import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Files_createTempFile {
    public void test_Files_createTempFile() {
        try {
            // Create a temp file with the prefix "tempFile" and suffix ".txt"
            Path tempFile =  Files.createTempFile("tempFile", ".txt");

            // The system default temporary-file directory will be used, and 
            // a cryptographically strong pseudo-random number generator will be used 
            // to generate names for the file.
            
            // Get the absolute file path of the temporary file
            String filePath = tempFile.toAbsolutePath().toString();

            // Print the file path
            System.out.println("Temporary file path: " + filePath);
            
        } catch (IOException e) {
            System.err.println("Failed to create temp file.");
            e.printStackTrace();
        }
    }
}