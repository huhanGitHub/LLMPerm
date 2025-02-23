public class Files_deleteIfExists {
    public void test_Files_deleteIfExists() {
        try {
            // Define the path to the file on the local file system that needs to be deleted
            Path filePath = FileSystems.getDefault().getPath("/path/to/file.txt");

            // Try to delete the file at the specified path, if it exists
            boolean isDeleted = Files.deleteIfExists(filePath);
            if (isDeleted) {
                System.out.println("The file was successfully deleted.");
            } else {
                System.out.println("The file does not exist, so it cannot be deleted.");
            }
        } catch (IOException ex) {
            System.out.println("An error occurred while attempting to delete the file: " + ex.getMessage());
        }
    }
}