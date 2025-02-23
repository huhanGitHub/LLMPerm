import java.net.CookieHandler;
import java.net.CookieManager;

public class CookieHandler_getDefault {
    public void test_CookieHandler_getDefault() {
        // Checking the default system-wide CookieHandler
        CookieHandler defaultCookieHandler = CookieHandler.getDefault();

        if (defaultCookieHandler == null) {
            System.out.println("No default CookieHandler found. Setting a new one.");

            // Creating a new CookieManager
            CookieManager cookieManager = new CookieManager();

            // Setting it as the default system-wide CookieHandler
            CookieHandler.setDefault(cookieManager);

            // Checking the default CookieHandler again
            defaultCookieHandler = CookieHandler.getDefault();

            if (defaultCookieHandler != null) {
                System.out.println("A new default CookieHandler has been set.");
            } else {
                System.out.println("Failed to set a new default CookieHandler!");
            }
        } else {
            System.out.println("Found a default CookieHandler.");
        }
    }
}