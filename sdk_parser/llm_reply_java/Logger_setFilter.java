import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Logger_setFilter {
    public void test_Logger_setFilter() {
        // Create a new Logger
        Logger logger = Logger.getLogger("MyLogger");

        // Set a Filter that only passes SEVERE LogRecords
        logger.setFilter(new Filter() {
            @Override
            public boolean isLoggable(LogRecord record) {
                if (record.getLevel() == Level.SEVERE) {
                    return true;
                } else {
                    return false;
                }
            }
        });

        // Try to log on different levels
        logger.log(Level.INFO, "This INFO message will be filtered out");
        logger.log(Level.SEVERE, "This SEVERE message will pass the filter");
    }
}