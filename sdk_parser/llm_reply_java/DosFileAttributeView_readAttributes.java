import java.nio.file.*;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.DosFileAttributeView;
import java.io.IOException;

public class DosFileAttributeView_readAttributes {
    public void test_DosFileAttributeView_readAttributes() {
        try {
            // Create a Path instance representing the file we want to access
            Path filePath = Paths.get("C:\\path\\to\\your\\file.txt");

            // Get the DosFileAttributeView of the file
            DosFileAttributeView dosFileAttributeView = Files.getFileAttributeView(
                    filePath, DosFileAttributeView.class);

            // Read the DOS attributes of the file
            DosFileAttributes dosFileAttributes = dosFileAttributeView.readAttributes();

            // Print out the DOS attributes
            System.out.println("isReadOnly: " + dosFileAttributes.isReadOnly());
            System.out.println("isHidden: " + dosFileAttributes.isHidden());
            System.out.println("isArchive: " + dosFileAttributes.isArchive());
            System.out.println("isSystem: " + dosFileAttributes.isSystem());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}