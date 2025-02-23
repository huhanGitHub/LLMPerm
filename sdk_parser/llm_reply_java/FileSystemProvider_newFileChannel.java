import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;
import java.util.Collections;

public class FileSystemProvider_newFileChannel {

    public void test_FileSystemProvider_newFileChannel() throws IOException {
        // Obtain the default FileSystemProvider
        FileSystemProvider provider = FileSystemProvider.installedProviders().get(0);

        // Get the path to the file location
        Path path = Paths.get("/path/to/test.txt");
         
        // Opening the file channel
        try (FileChannel fileChannel = provider.newFileChannel(path, Collections.emptySet())) {
            ByteBuffer buf = ByteBuffer.allocate(1024);
            while (fileChannel.read(buf) != -1) {
                buf.rewind();
                System.out.print(new String(buf.array(), 0, buf.limit()));
                buf.flip();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}