public class File_mkdir {
    public boolean test_File_mkdir() {
        // Specify the path of the directory.
        String dirPath = "/sdcard/NewDirectory";

        // Create an instance of file.
        File newDirectory = new File(dirPath);

        // Check if the directory already exists.
        if (!newDirectory.exists()) {
            // Attempt to create the directory.
            return newDirectory.mkdir(); // Returns true if creation was successful.
        }

        // If the directory already exists, return false.
        return false;
    }
}