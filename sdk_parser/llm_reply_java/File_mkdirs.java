public class File_mkdirs {
    public boolean test_File_mkdirs() {
        // Set the directory path that you want to create.
        // This path is a directory inside app's private storage.
        String dirPath = getApplicationContext().getFilesDir().getAbsolutePath() + "/testDir/subDir";

        // Create a File object.
        File newDir = new File(dirPath);

        // Call mkdirs() method to create the directory, including any 
        // necessary but nonexistent parent directories.
        // Returns true if the directories were created, false otherwise.
        boolean result = newDir.mkdirs();

        // Log the result.
        if(result) {
            Log.d("Directory Create", "Directory created successfully at " + dirPath);
        } else {
            Log.d("Directory Create", "Directory creation failed at " + dirPath);
        }

        // Return the result.
        return result;
    }
}