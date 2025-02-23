import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.UserDefinedFileAttributeView;

public class UserDefinedFileAttributeView_write {
  
    public void test_UserDefinedFileAttributeView_write() {
        try {
            Path path = Paths.get("/your/file/path");
            
            // retrieve UserDefinedFileAttributeView
            UserDefinedFileAttributeView view = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);

            // Set custom attribute
            String name = "user.mimeType";
            String value = "text/html";
            view.write(name, Charset.defaultCharset().encode(value));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}