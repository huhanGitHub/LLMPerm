import java.io.File;

public class File_getUsableSpace {
    public void test_File_getUsableSpace() {
        // Referencing a location in the file system. 
        // In this case, it is the root directory of the SD Card.
        File file = new File("/storage/emulated/0/");

        // Here, getUsableSpace() method is called on the file object.
        long usableSpace = file.getUsableSpace();

        // The method returns the available space in bytes.
        // Convert it to MB for easier readability by dividing it by (1024 * 1024)
        double usableSpaceInMB = usableSpace / (double) (1024 * 1024);

        // Print the usable space in MB
        System.out.println("Usable space (MB): " + usableSpaceInMB);
    }
}