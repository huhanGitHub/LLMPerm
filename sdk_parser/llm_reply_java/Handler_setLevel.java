import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Handler_setLevel {
    private Logger logger;
    private ConsoleHandler handler;

    public void test_Handler_setLevel() {
        try {
            // create a Logger instance
            logger = Logger.getLogger(Handler_setLevel.class.getName());

            // create a ConsoleHandler instance
            handler = new ConsoleHandler();

            // set the level of the ConsoleHandler to the desired level
            handler.setLevel(Level.INFO);

            // add the handler to the logger
            logger.addHandler(handler);

            // log a message to test
            logger.info("This is an info message for testing ");
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}