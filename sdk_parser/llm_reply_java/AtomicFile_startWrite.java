import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.AtomicFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AtomicFile_startWrite {

    public void test_AtomicFile_startWrite(Context context){
        // Permission check
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Request permission
            ActivityCompat.requestPermissions(thisActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            // Use AtomicFile
            AtomicFile atomicFile = new AtomicFile(new File(context.getFilesDir(), "test.txt"));
            
            FileOutputStream outputStream = null;
            try {
                // Start write
                outputStream = atomicFile.startWrite();
                outputStream.write("Test content".getBytes());

                // Finish and close the stream
                atomicFile.finishWrite(outputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                if (outputStream != null) {
                    atomicFile.failWrite(outputStream);
                }
            } catch (IOException e) {
                e.printStackTrace();
                if (outputStream != null) {
                    atomicFile.failWrite(outputStream);
                }
            }
        }
    }
}