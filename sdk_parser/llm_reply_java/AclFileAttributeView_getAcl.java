import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclFileAttributeView;
import java.util.List;

public class AclFileAttributeView_getAcl {  
  public void test_AclFileAttributeView_getAcl() {
    Path file = Paths.get("/path/to/your/file.txt"); // replace with your file path

    // get AclFileAttributeView
    AclFileAttributeView aclAttr 
          = Files.getFileAttributeView(file, AclFileAttributeView.class);

    try {
        List<AclEntry> acl = aclAttr.getAcl();
        for (AclEntry aclEntry : acl) {
            System.out.println(
                  String.format("Principal: %s, Type: %s, Permissions: %s, Flags: %s",
                        aclEntry.principal().getName(),
                        aclEntry.type(),
                        aclEntry.permissions(), 
                        aclEntry.flags()));
        }
    } catch (IOException e) {
        System.out.println("Failed getting ACL entries: " + e.getMessage());
    }
  }
}