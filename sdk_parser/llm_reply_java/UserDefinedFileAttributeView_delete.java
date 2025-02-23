import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.UserDefinedFileAttributeView;

public class UserDefinedFileAttributeView_delete {
    public void test_UserDefinedFileAttributeView_delete() {
        // Specifying the path of the file
        Path path = Paths.get("filepath.txt");
        UserDefinedFileAttributeView view = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);

        // Name of the user-defined attribute to delete
        String name = "myUserAttr";

        try {
            view.delete(name);
            System.out.println("Attribute " + name + " has been deleted successfully.");
        } catch (IOException e) {
            System.out.println("Error deleting attribute: " + e.getMessage());
        }
    }
}