import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

public class Files_getPosixFilePermissions {

    public void test_Files_getPosixFilePermissions() {
        try {
            // Considering you are in a Unix-like system
            Path filePath = FileSystems.getDefault().getPath("/path/to/your/file.txt");

            // Fetch the permissions
            Set<PosixFilePermission> posixFilePermissions = Files.getPosixFilePermissions(filePath);

            // Convert to String form
            String permissions = PosixFilePermissions.toString(posixFilePermissions);
            System.out.println("Permissions: " + permissions);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}