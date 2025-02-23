import java.security.AccessController;
import java.security.PrivilegedAction;
import java.io.File;

public class AccessController_doPrivileged {
    
    public File test_AccessController_doPrivileged() {
        return AccessController.doPrivileged(
                new PrivilegedAction<File>() {
                    public File run() {
                        File file = new File("testfile.txt");
                        if (file.exists()) {
                            System.out.println("File exists");
                        } else {
                            System.out.println("File does not exist");
                        }
                        return file;
                    }
                }
        );
    }
}