import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Files_readAllBytes {
    public void test_Files_readAllBytes() throws IOException {
        // Creating Path to the file.
        Path filePath = Paths.get("/path/to/your/file.txt");
        // Reading all bytes from the file
        byte[] allBytes = Files.readAllBytes(filePath);

        // Use the byte array as needed, for example, convert to string if text file.
        String content = new String(allBytes);

        System.out.println("File content: " + content);
    }
}