import android.os.Environment;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.spi.FileSystemProvider;
import java.nio.channels.AsynchronousFileChannel;
import java.util.HashSet;
import java.util.Arrays;

public class FileSystemProvider_newAsynchronousFileChannel {

    public void test_FileSystemProvider_newAsynchronousFileChannel() {
        FileSystemProvider provider = FileSystemProvider.installedProviders().get(0);
        AsynchronousFileChannel fileChannel = null;
        try {
            String testFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/testfile.txt";
            fileChannel = provider.newAsynchronousFileChannel(Paths.get(testFilePath), new HashSet<>(Arrays.asList(StandardOpenOption.CREATE, 
            StandardOpenOption.READ, StandardOpenOption.WRITE)), null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileChannel != null) {
                try {
                    fileChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}