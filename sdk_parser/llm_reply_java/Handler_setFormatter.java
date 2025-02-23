public class Handler_setFormatter {
    public void test_Handler_setFormatter() {
        // Define a Handler
        Handler handler = new java.util.logging.ConsoleHandler();

        // Set a formatter to the handler
        handler.setFormatter(new java.util.logging.SimpleFormatter() {
            @Override
            public String format(LogRecord record) {
                // Customize the formatting of logs
                return "[" + record.getLevel() + "]" + record.getLoggerName() + " : " + record.getMessage() + "\n";
            }
        });

        // Create a Logger instance 
        Logger logger = Logger.getLogger(MainActivity.class.getName());

        // Set handler to the logger
        logger.addHandler(handler);

        // Log a info message
        logger.log(Level.INFO, "This is a info log message");

        // Log a severe message
        logger.log(Level.SEVERE, "This is a severe log message");
    }
}