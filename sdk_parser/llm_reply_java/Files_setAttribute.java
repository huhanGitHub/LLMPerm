import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Files_setAttribute {
    public void test_Files_setAttribute() {
        // Specify the file path
        Path path = Paths.get("/path/to/your/file");

        // Specify attribute and its new value
        String attribute = "basic:lastModifiedTime";
        long newValue = System.currentTimeMillis();

        try {
            // Set file attribute
            Files.setAttribute(path, attribute, newValue);
            System.out.println("Attribute " + attribute + " has been set to: " + newValue);

            // Confirm the change
            Object value = Files.getAttribute(path, attribute);
            System.out.println("Attribute " + attribute + " is: " + value);
        } catch (IOException ex) {
            System.err.println("Exception while setting file attribute: " + ex.getMessage());
        }
    }
}