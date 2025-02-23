import java.io.File;

public class File_exists {
    public void test_File_exists() {
        // Specify a path to the file
        String filePath = "/path/to/your/file.txt";

        // Create a File object
        File file = new File(filePath);

        // Check if the file exists
        boolean fileExists = file.exists();

        // Print the result
        if (fileExists) {
            System.out.println("The file exists.");
        } else {
            System.out.println("The file does not exist.");
        }
    }
}