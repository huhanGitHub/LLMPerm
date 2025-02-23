public class File_getFreeSpace {
    private void test_File_getFreeSpace() {
        // Create a File object. In this case, referring to the external storage directory
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

        // Check if the file/directory exists
        if (file.exists()) {
            // Get the free space in bytes
            long freeBytes = file.getFreeSpace();

            // Convert the free space to megabytes
            long freeMegabytes = freeBytes / (1024 * 1024);

            // Display the free space
            Toast.makeText(getApplicationContext(),
                    "Free space: " + freeMegabytes + "MB",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),
                    "File or Directory does not exist",
                    Toast.LENGTH_SHORT).show();
        }
    }
}