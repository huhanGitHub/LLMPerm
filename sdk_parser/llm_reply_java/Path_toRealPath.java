import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Path_toRealPath {
 
    public void test_Path_toRealPath() {
        Path path = Paths.get("/user/.././root","home","user","documents");
        try {
            Path realPath = path.toRealPath();
            System.out.println("Real path: " + realPath.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}