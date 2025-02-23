import android.util.AtomicFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AtomicFile_rename {

    public void test_AtomicFile_rename() {
        AtomicFile atomicFile = null;
        FileOutputStream fileOutputStream = null;

        try {
            File oldFile = new File(getFilesDir(), "oldFileName.txt");
            atomicFile = new AtomicFile(oldFile);
            fileOutputStream = atomicFile.startWrite();

            File newFile = new File(getFilesDir(), "newFileName.txt");
            boolean isRenamed = oldFile.renameTo(newFile);

            if(!isRenamed){
                throw new IOException("Failed to rename file");
            }
            atomicFile.finishWrite(fileOutputStream);
            Log.d("Test", "Successfully renamed. AtomicFile is used.");

        } catch (IOException e) {
            Log.e("Test", "Failed to use AtomicFile", e);
            if (atomicFile != null) {
                atomicFile.failWrite(fileOutputStream);
            }
        } finally {
            if (fileOutputStream != null) {
                try { fileOutputStream.close(); } catch (IOException e) { e.printStackTrace(); }
            }
        }
    }
}