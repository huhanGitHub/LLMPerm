import java.io.File;
import android.util.Log;

public class File_renameTo {
    public boolean test_File_renameTo() {
        // Create an old file object
        File oldFile = new File("/path/to/oldfile.txt");

        // Check if the file exists
        if (!oldFile.exists()) {
            Log.e("FileRenameTest", "Old file doesn't exist");
            return false;
        }

        // Create a new file object with a new name
        File newFile = new File("/path/to/newfile.txt");

        // Check if file with new name already exists
        if (newFile.exists()) {
            Log.e("FileRenameTest", "File with new name already exists");
            return false;
        }

        // Rename the old file
        boolean renameSuccessful = oldFile.renameTo(newFile);

        if (renameSuccessful) {
            Log.i("FileRenameTest", "File renamed successfully");
        } else {
            Log.e("FileRenameTest", "File rename failed");
        }

        return renameSuccessful;
    }
}