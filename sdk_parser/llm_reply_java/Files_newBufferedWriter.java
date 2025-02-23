import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.charset.StandardCharsets;

public class Files_newBufferedWriter {

    public void test_Files_newBufferedWriter() {
        Path path = Paths.get("example.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE)) {
            writer.write("This is a test");
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }
}