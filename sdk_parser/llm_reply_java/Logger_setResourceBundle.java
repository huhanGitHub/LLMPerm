import java.util.logging.Logger;
import java.util.ResourceBundle;

public class Logger_setResourceBundle {

    public void test_Logger_setResourceBundle() {
        // Create a new Logger instance
        Logger logger = Logger.getLogger("com.myapp");

        // Create a ResourceBundle
        ResourceBundle bundle = ResourceBundle.getBundle("com.myapp.MyBundle");

        // Set the logger's resource bundle
        logger.setResourceBundle(bundle);

        // Log a message
        logger.info("Testing setResourceBundle");

        // The output will be different depending on your defined resource bundle and 
        // your system's current default locale.
    }
}