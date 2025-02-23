import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Handler_setFilter {
    void test_Handler_setFilter() {
        Logger logger;
        Handler handler;
        try {
            // Create a Logger
            logger = Logger.getLogger(Handler_setFilter.class.getName());

            // Get the Handler from Logger
            handler = logger.getHandlers()[0];

            // Define a simple filter which only logs INFO
            handler.setFilter(new Filter() {
                @Override
                public boolean isLoggable(LogRecord record) {
                    return record.getLevel().getName().equalsIgnoreCase("INFO");
                }
            });

            logger.info("This is an INFO message.");
            logger.warning("This is a WARNING message.");

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Now, only the INFO message will be logged
    }
}