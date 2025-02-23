import java.io.File;
import java.io.IOException;

public class File_createTempFile {
    public void test_File_createTempFile() {
        try {
            // Create a temporary file with "test" as prefix and ".txt" as suffix
            File tempFile = File.createTempFile("test", ".txt");

            // Check if the file is created successfully
            if(tempFile.exists()) {
                System.out.println("Temp file was created at path: " + tempFile.getAbsolutePath());
            } else {
                System.out.println("Failed to create temp file.");
            }

            // Always remember to delete the file when it's no longer needed
            boolean deleted = tempFile.delete();
            if (deleted) {
                System.out.println("Temp file was deleted.");
            } else {
                System.out.println("Failed to delete temp file.");
            }

        } catch (IOException e) {
            // Failed to create temporary file, print the stack trace
            e.printStackTrace();
        }
    }
}