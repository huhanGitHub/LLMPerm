import android.util.Log;
import java.util.logging.*;

public class Logger_setLevel {
    public void test_Logger_setLevel() {
        // Create a Logger
        Logger logger = Logger.getLogger("MyLogger");

        try {
            // Set level to SEVERE
            logger.setLevel(Level.SEVERE);
            Log.d("Logger_Test", "Logger level set to: " + logger.getLevel());

            // Generate a severe level log message
            logger.severe("This is a severe message");
            // Note: This message will be displayed as the logger level is set to SEVERE

            // Set level to INFO
            logger.setLevel(Level.INFO);
            Log.d("Logger_Test", "Logger level set to: " + logger.getLevel());

            // Generate a severe level log message and an info level log message
            logger.severe("This is a severe message");
            logger.info("This is an info message");
            // Note: Both messages will be displayed as the logger level is set to INFO

            // Set level to OFF
            logger.setLevel(Level.OFF);
            Log.d("Logger_Test", "Logger level set to: " + logger.getLevel());

            // Generate a severe level log message and an info level log message
            logger.severe("This is a severe message");
            logger.info("This is an info message");
            // Note: None of the messages will be displayed as the logger level is set to OFF

        } catch (SecurityException se) {
            Log.e("Logger_Test", "SecurityException occured: ", se);
        } catch (RuntimeException re) {
            Log.e("Logger_Test", "RuntimeException occured: ", re);
        }
    }
}