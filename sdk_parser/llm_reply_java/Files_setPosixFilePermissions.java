import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;
import java.util.HashSet;

public class Files_setPosixFilePermissions {
    public void test_Files_setPosixFilePermissions() {
        // the path of the file you want to modify
        Path filePath = Paths.get("/path/to/your/file");

        // defining new permissions
        Set<PosixFilePermission> permissions = new HashSet<>();
        permissions.add(PosixFilePermission.OWNER_READ);
        permissions.add(PosixFilePermission.OWNER_WRITE);
        permissions.add(PosixFilePermission.GROUP_READ);
        permissions.add(PosixFilePermission.OTHERS_READ);

        // setting new permissions
        try {
            Files.setPosixFilePermissions(filePath, permissions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}