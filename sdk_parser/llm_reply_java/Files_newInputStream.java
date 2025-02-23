import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.InputStream;
import java.io.IOException;

public class Files_newInputStream {
    public void test_Files_newInputStream() {
        Path path = Paths.get("/path/to/your/file.txt");
        
        try {
            InputStream inputStream = Files.newInputStream(path);
            // Use the InputStream in here
            
            System.out.println("Data from the file: ");
            int data = inputStream.read();
            while (data != -1) {
                System.out.print((char) data);
                data = inputStream.read();
            }
            inputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}