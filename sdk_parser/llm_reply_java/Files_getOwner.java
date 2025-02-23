import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.UserPrincipal;

public class Files_getOwner {
    public void test_Files_getOwner() {
        Path path = Paths.get("/path/to/file");

        try {
            UserPrincipal owner = Files.getOwner(path);
            System.out.println("Owner: " + owner.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Files_getOwner test = new Files_getOwner();
        test.test_Files_getOwner();
    }
}