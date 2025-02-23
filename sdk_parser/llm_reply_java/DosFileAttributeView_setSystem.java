import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.DosFileAttributeView;

public class DosFileAttributeView_setSystem {

    public static void test_DosFileAttributeView_setSystem() {
        Path filePath = FileSystems.getDefault().getPath("test_file.txt");

        DosFileAttributeView dosView = Files.getFileAttributeView(filePath, DosFileAttributeView.class);

        try {
            boolean isSystem = dosView.readAttributes().isSystem();
            System.out.println("Before operation, isSystem: " + isSystem);

            // Set the 'system' flag of the file.
            dosView.setSystem(true);
            isSystem = dosView.readAttributes().isSystem();
            System.out.println("After operation, isSystem: " + isSystem);

        } catch (IOException exception) {
            System.out.println("Error while reading/writing file attributes: " + exception.getMessage());
        }
    }

    public static void main(String[] args) {
        test_DosFileAttributeView_setSystem();
    }
}