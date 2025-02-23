import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Files_exists {
    public void test_Files_exists() {
        String filePath = "your actual file path"; 
        Path path = Paths.get(filePath);
        
        boolean fileExists = Files.exists(path);

        if(fileExists) {
            System.out.println("File exists at path: " + filePath);
        } else {
            System.out.println("File does not exist at path: " + filePath);
        }
    }
}