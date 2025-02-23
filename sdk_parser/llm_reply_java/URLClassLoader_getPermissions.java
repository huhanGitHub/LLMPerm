import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.cert.Certificate;
import java.util.Collections;
import java.security.Permission;

public class URLClassLoader_getPermissions {
    private void test_URLClassLoader_getPermissions() {
        try {
            // Creating a new instance of URLClassLoader
            File file = new File("/src/test");  // indicate the correct path 
            URL url = file.toURI().toURL();
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url});
            
            // The location from where to load the classes
            CodeSource codeSource = new CodeSource(url, (Certificate[]) null);
            
            // Getting the permissions of the URL
            PermissionCollection permissionCollection = urlClassLoader.getPermissions(codeSource);
            
            // Enumerating and printing each permission
            for (Permission permission : Collections.list(permissionCollection.elements())) {
                System.out.println(permission);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}