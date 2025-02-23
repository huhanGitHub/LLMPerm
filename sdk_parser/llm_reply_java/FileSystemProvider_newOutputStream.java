import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;
import java.util.List;
import android.os.Build;
import androidx.annotation.RequiresApi;

public class FileSystemProvider_newOutputStream {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void test_FileSystemProvider_newOutputStream() {
        List<FileSystemProvider> providers = FileSystemProvider.installedProviders();

        for (FileSystemProvider provider : providers) {
            try {
                if (provider.getScheme().equalsIgnoreCase("file")) {
                    OutputStream outputStream = provider.newOutputStream(Paths.get("/path/to/your/output/file.txt"));
                    BufferedOutputStream bos = new BufferedOutputStream(outputStream);

                    String textToWrite = "Test string to be written to file!";
                    bos.write(textToWrite.getBytes());
                    bos.flush(); // Make sure all data is sent
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}