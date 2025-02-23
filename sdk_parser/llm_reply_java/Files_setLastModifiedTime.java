import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Files_setLastModifiedTime {
    public void test_Files_setLastModifiedTime() {
        // Specify path to a file
        Path filePath = Paths.get("/path/to/your/file.txt");

        // Create a file time instance for current time
        LocalDateTime now = LocalDateTime.now();
        FileTime fileTime = FileTime.from(now.atZone(ZoneId.systemDefault()).toInstant());

        try {
            // Set last modified time
            Files.setLastModifiedTime(filePath, fileTime);
        } catch (IOException ex) {
            // Handle exception
            ex.printStackTrace();
        }
    }
}