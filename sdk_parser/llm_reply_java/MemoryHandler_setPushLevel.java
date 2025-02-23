import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.MemoryHandler;

public class MemoryHandler_setPushLevel {
    public void test_MemoryHandler_setPushLevel() {
        // Create a new logger object
        Logger logger = Logger.getLogger("com.example.myapp");

        // Create a new console handler
        ConsoleHandler consoleHandler = new ConsoleHandler();

        // Create a new memory handler with default constructor
        MemoryHandler memoryHandler = new MemoryHandler();

        try {
            // Set the handler level
            memoryHandler.setLevel(Level.ALL);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        // Set the push level
        memoryHandler.setPushLevel(Level.SEVERE);

        // Add the memory handler to the logger
        logger.addHandler(memoryHandler);

        // Log a simple info message
        logger.info("This is a simple log message");

        // Log a severe message which will trigger a push
        logger.severe("This is a severe log message");
    }
}