public class File_isDirectory {
    private void test_File_isDirectory() {
        // Specify the path.
        String path = Environment.getExternalStorageDirectory().toString();

        // Create a File object for the specified path.
        File file = new File(path);

        // Check if the path represents a directory using isDirectory() method.
        if (file.isDirectory()) {
            // This path represents a directory.
            Toast.makeText(this, path + " is a directory.", Toast.LENGTH_SHORT).show();
        } else {
            // This path does not represent a directory.
            Toast.makeText(this, path + " is not a directory.", Toast.LENGTH_SHORT).show();
        }
    }
}