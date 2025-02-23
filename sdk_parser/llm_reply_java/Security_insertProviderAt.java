import java.security.Provider;
import java.security.Security;

public class Security_insertProviderAt {

    public void test_Security_insertProviderAt() {
        try {
            // Create a new test provider
            Provider provider = new Provider("TestProvider", 1.0, "Test Provider for insertProviderAt") {
            };

            // Insert the provider into the security list at a particular position
            final int position = 2; // please change it with your needs
            int resultCode = Security.insertProviderAt(provider, position);

            if (resultCode == -1) {
                System.out.println("Failed to insert the provider at the desired position");
            } else {
                System.out.println("Test provider was successfully inserted at the desired position");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}