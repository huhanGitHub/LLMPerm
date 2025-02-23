public class File_delete {
    public boolean test_File_delete() {
        File file = new File("path/to/your/file");

        // Checks if file exists
        if (file.exists()) {
            // Deletes the file
            return file.delete();
        }
        return false;    // Returns false if the file does not exist
    }
}