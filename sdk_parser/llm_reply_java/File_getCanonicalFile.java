import android.util.Log;
import java.io.File;
import java.io.IOException;

public class File_getCanonicalFile {
    public void test_File_getCanonicalFile() {
        String path = "/data/user/0/com.example.myapp/files/original.txt";
        File file = new File(path);

        try {
            File canonicalFile = file.getCanonicalFile();
            Log.d("Canonical File Test", "Canonical File Path : " + canonicalFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Canonical File Test", "Error getting canonical file", e);
        }
    }
}