import java.util.logging.Logger;

public class Logger_setUseParentHandlers {
    
    public void test_Logger_setUseParentHandlers() {
        // Create a Logger instance
        Logger logger = Logger.getLogger("MyApp");

        // Log a simple INFO message
        logger.info("This is a simple log message.");

        // By default, a Logger sends its output to its parent Logger
        // This behaviour can be changed using the setUseParentHandlers method.

        // Now, let's disable sending output to the parent logger.
        logger.setUseParentHandlers(false);

        // Log another message. After calling 'setUseParentHandlers(false)', this won't be sent to the parent logger.
        logger.info("This message won't be sent to the parent logger.");

        // If you want to revert this, and allow the logger to send its output to its parent, call 'setUseParentHandlers(true)'.
        logger.setUseParentHandlers(true);

        // Log yet another message. This time it will be passed to the parent logger as well.
        logger.info("This will be sent to the parent logger.");
    }
}