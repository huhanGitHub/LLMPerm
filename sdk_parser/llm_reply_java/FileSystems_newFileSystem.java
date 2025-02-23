import java.nio.file.*;
import java.io.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class FileSystems_newFileSystem {

    public void test_FileSystems_newFileSystem() throws IOException {
        // file system URI
        URI fileSystemUri = URI.create("jar:file:/test.zip");

        // initialize environment map
        Map<String, String> env = new HashMap<>();
        env.put("create", "true");

        // open or create the file system
        try (FileSystem fs = FileSystems.newFileSystem(fileSystemUri, env)) {
            // use fs to work with the file system
            System.out.println("File System: " + fs.toString());
        }
    }
}