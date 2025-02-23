import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class Authenticator_setDefault {

    public void test_Authenticator_setDefault() {
        // replace "username" and "password" with actual username and password.
        String userName = "username";
        String password = "password";

        // Create an Authenticator instance which returns a PasswordAuthentication instance with your username and password
        Authenticator myAuth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password.toCharArray());
            }
        };

        // Set the system-wide default Authenticator
        Authenticator.setDefault(myAuth);
    }
}