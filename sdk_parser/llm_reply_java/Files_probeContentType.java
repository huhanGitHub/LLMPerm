import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

public class Files_probeContentType {
    public String test_Files_probeContentType() {
        String mimeType = "";
        try {
            Path path = Paths.get("/path/to/your/file");
            mimeType = Files.probeContentType(path);
            System.out.println("The detected MIME type is: " + mimeType);
        } catch (IOException e) {
            System.err.println("Error probing MIME type: " + e.getMessage());
        }
        return mimeType;
    }
}