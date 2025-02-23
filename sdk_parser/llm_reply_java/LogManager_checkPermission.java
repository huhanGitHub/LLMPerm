import java.util.logging.LogManager;
import java.util.logging.LoggingPermission;

public class LogManager_checkPermission {
    public void test_LogManager_checkPermission() {
        try {
            //get the log manager instance
            LogManager logManager = LogManager.getLogManager();

            //create a logging permission
            LoggingPermission permission = new LoggingPermission("control", null);

            // check for permission
            logManager.checkPermission();

            //permission checking is successful, print success message
            System.out.println("Permission check passed successfully.");

        } catch (SecurityException e) {
            // Handles logging permission security exception, print error message
            System.out.println("Permission check failed: " + e.getMessage());
        }
    }
}