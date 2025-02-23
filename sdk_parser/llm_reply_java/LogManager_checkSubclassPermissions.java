import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LogManager_checkSubclassPermissions {

    private void test_LogManager_checkSubclassPermissions() {
        try {
            LogManager logManager = LogManager.getLogManager();
            Logger logger = Logger.getLogger(getClass().getName());

            // Create a subclass of LogManager
            class MyLogManager extends LogManager {}

            MyLogManager myLogManager = new MyLogManager();

            logger.info("Subclass created: " + myLogManager);

        } catch (SecurityException e) {
            // If the invocation of checkSubclassPermissions throws a security exception,
            // it means that LogManager has indeed a subclass
            Logger logger = Logger.getLogger(getClass().getName());
            logger.severe("Subclass is not permitted");
        }
    }
}