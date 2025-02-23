import java.security.Identity;
import java.security.IdentityScope;
import java.security.KeyManagementException;

public class Identity_setInfo {
    public void test_Identity_setInfo() {
        try {
            // Create identity
            Identity identity = new Identity("TestIdentity", new IdentityScope("TestScope"));

            // Set Info
            identity.setInfo("Additional Info for this Identity");

            System.out.println("Identity Info: " + identity.getInfo());

        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }
}