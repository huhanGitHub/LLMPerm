public class File_setReadOnly {
    public boolean test_File_setReadOnly() {
        try {
            // Creating a new File instance
            File myFile = new File("/sdcard/myfile.txt");

            // Check if the file exists
            if (myFile.exists()) {
                // Set the file to read only
                boolean isReadOnly = myFile.setReadOnly();

                // If the operation was successful
                if (isReadOnly) {
                    return true;                
                } else {
                    // If the operation was not successful
                    Toast.makeText(MainActivity.this, "Failed to set file to read-only", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                Toast.makeText(MainActivity.this, "File does not exist", Toast.LENGTH_SHORT).show();
                return false;               
            }             
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "An error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;   
        }
    }
}