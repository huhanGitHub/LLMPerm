import java.security.Permission;
import java.security.Permissions;
import java.io.FilePermission;

public class Permissions_add {
    public void test_Permissions_add()  {
        // Create a new Permissions object
        Permissions permissions = new Permissions();

        // Create two new FilePermission objects
        FilePermission readPerm = new FilePermission("/home/nobody/","read");
        FilePermission writePerm = new FilePermission("/home/nobody/","write");

        // Add the FilePermission objects to the Permissions object
        permissions.add(readPerm);
        permissions.add(writePerm);

        // Verifying permissions
        if(!permissions.implies(new FilePermission("/home/nobody/","read"))){
            System.out.println("Read permission not correctly added.");
        }
        if(!permissions.implies(new FilePermission("/home/nobody/","write"))){
            System.out.println("Write permission not correctly added.");
        }
    }
}