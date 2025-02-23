public class Handler_getErrorManager {
    public void test_Handler_getErrorManager() {
        try {
            // Create a Logger
            Logger logger = Logger.getLogger(Handler_getErrorManager.class.getName());

            // Create a Handler
            Handler handler = new ConsoleHandler();

            // Assign handler to the logger
            logger.addHandler(handler);

            // Get the ErrorManager from the Handler
            ErrorManager errorManager = handler.getErrorManager();

            // If errorManager is null, handler doesn't have an errorManager
            if (errorManager == null)
                System.out.println("Handler doesn't have an ErrorManager");
            else
                System.out.println("Handler has an ErrorManager: " + errorManager.toString());
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }
}