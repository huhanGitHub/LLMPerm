import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;
import java.util.logging.SimpleFormatter;

public class StreamHandler_setOutputStream {
    public void test_StreamHandler_setOutputStream() {
        try {
            // Create a ByteArrayOutputStream stream
            OutputStream outputStream = new ByteArrayOutputStream();

            // Create a StreamHandler
            StreamHandler streamHandler = new StreamHandler();
            
            // Set the OutputStream of the StreamHandler
            streamHandler.setOutputStream(outputStream);
            
            // Create a SimpleFormatter
            SimpleFormatter formatter = new SimpleFormatter();

            // Set the Formatter of the StreamHandler
            streamHandler.setFormatter(formatter);

            // Create a Logger
            Logger logger = Logger.getAnonymousLogger();

            // Add the StreamHandler to the Logger
            logger.addHandler(streamHandler);
            
            // Log some message
            logger.info("This is a test message");

            // Flush the StreamHandler to ensure the log message is sent to the OutputStream
            streamHandler.flush();
            
            // Convert the OutputStream to String and print it
            String logMessage = outputStream.toString();
            System.out.println(logMessage);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}