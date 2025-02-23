public class Files_getAttribute {
    public void test_Files_getAttribute() {
        try {
            // Create a Path instance from file path
            Path filePath = Paths.get(getFilesDir().getAbsolutePath() + "/testfile.txt"); // use your file path

            // Use Files.getAttribute to get last modification time
            FileTime lastModifiedTime = (FileTime) Files.getAttribute(filePath, "lastModifiedTime");

            // Log the output
            Log.d("File Attribute", "Last modified time: " + lastModifiedTime.toString());
        } catch (Exception e) {
            // Handle the exception
            Log.e("Error", "Error occurred while getting attribute: " + e.getMessage());
        }
    }
}