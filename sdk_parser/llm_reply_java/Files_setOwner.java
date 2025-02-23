import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;

public class Files_setOwner {
    public void test_Files_setOwner() {
        try {
            Path path = FileSystems.getDefault().getPath("/path/to/your/file");
            FileOwnerAttributeView ownerAttributeView = Files.getFileAttributeView(path, FileOwnerAttributeView.class);
            UserPrincipalLookupService lookupService = FileSystems.getDefault().getUserPrincipalLookupService();
            UserPrincipal newOwner = lookupService.lookupPrincipalByName("newOwner");
            ownerAttributeView.setOwner(newOwner);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}