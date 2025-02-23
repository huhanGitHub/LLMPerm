public class Handler_close {
    public void test_Handler_close() {
        try {
            // Setup a Logger
            Logger logger = Logger.getLogger(Handler_close.class.getName());

            // Adding a Console Handler to Logger
            Handler consoleHandler = new ConsoleHandler();
            logger.addHandler(consoleHandler);

            // Remove Handler
            consoleHandler.close();

            logger.info("This is a test log message");

        } catch (SecurityException e) {
            System.out.println("SecurityException: " + e.getMessage());
        }
    }
}