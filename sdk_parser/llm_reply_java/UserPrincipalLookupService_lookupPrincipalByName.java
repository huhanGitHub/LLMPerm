import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;

public class UserPrincipalLookupService_lookupPrincipalByName {

    public void test_UserPrincipalLookupService_lookupPrincipalByName() {
        UserPrincipalLookupService lookupService = FileSystems.getDefault().getUserPrincipalLookupService();

        String principalName = "user";
        try {
            UserPrincipal principal = lookupService.lookupPrincipalByName(principalName);
            System.out.println(principal.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}