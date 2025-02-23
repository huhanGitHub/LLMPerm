import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.spi.FileTypeDetector;
import java.io.IOException;

public class FileTypeDetector_checkPermission {
    public void test_FileTypeDetector_checkPermission() {
        final Path path = Paths.get("/path/to/your/file");

        FileTypeDetector fileTypeDetector = new FileTypeDetector() {
            @Override
            public String probeContentType(Path path) throws IOException {
                if (!Files.isReadable(path)) {
                    throw new IOException("Insufficient permissions...");
                }

                return null; // Implement your logic here to detect file type based on the path.
            }
        };

        String detectedType;
        try {
            detectedType = fileTypeDetector.probeContentType(path);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (detectedType == null) {
            System.out.println("Could not detect the file type.");
        } else {
            System.out.println("Detected file type: " + detectedType);
        }
    }
}