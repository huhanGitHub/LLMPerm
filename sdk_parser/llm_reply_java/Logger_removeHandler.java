import java.util.Arrays;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Logger_removeHandler {
    public void test_Logger_removeHandler() {
        // Create a new logger instance
        Logger logger = Logger.getLogger("TestLogger");

        // Create custom handler
        Handler handler = new Handler() {
            @Override
            public void publish(LogRecord record) {
                // custom publish logic here
            }
            @Override
            public void flush() {
                // custom flush logic here
            }
            @Override
            public void close() throws SecurityException {
                // custom close logic here
            }
        };

        // Add the handler to the logger
        logger.addHandler(handler);

        // Remove the handler from the logger
        logger.removeHandler(handler);

        // Check if the handler is removed successfully.
        boolean hasHandler = Arrays.asList(logger.getHandlers()).contains(handler);
        System.out.println("Is handler removed? : " + !hasHandler);
    }
}