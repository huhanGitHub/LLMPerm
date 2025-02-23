import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.*;

public class BasicFileAttributeView_setTimes {

    public void test_BasicFileAttributeView_setTimes() {
        // obtain the path to the file
        Path path = Paths.get("/path/to/file.txt");

        // obtain the BasicFileAttributeView
        BasicFileAttributeView view = Files.getFileAttributeView(path, BasicFileAttributeView.class);

        // prepare new times
        FileTime lastModifiedTime = FileTime.fromMillis(System.currentTimeMillis());
        FileTime lastAccessTime = FileTime.fromMillis(System.currentTimeMillis());
        FileTime createTime = FileTime.fromMillis(System.currentTimeMillis());

        try {
            // set the times
            view.setTimes(lastModifiedTime, lastAccessTime, createTime);
        } catch (IOException e) {
            // handle exception
            e.printStackTrace();
        }
    }
}