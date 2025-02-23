import java.io.IOException;
import java.nio.file.*;

public class Files_delete {
    public static void test_Files_delete() {
        // Define the path of the file to delete
        Path path = Paths.get("path/to/file.txt");
        
        try {
            // Delete the file
            Files.delete(path);
            System.out.println("File was successfully deleted.");
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", path);
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x);
        }
    }
}