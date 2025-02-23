public class Logger_addHandler {
    public void test_Logger_addHandler() {
        Logger logger = Logger.getLogger(Logger_addHandler.class.getName());

        Handler handler = new Handler() {
            @Override
            public void publish(LogRecord record) {
                if (getFormatter() == null) {
                    setFormatter(new SimpleFormatter());
                }

                try {
                    String message = getFormatter().format(record);
                    if (record.getLevel().intValue() >= getLevel().intValue()) {
                        System.out.println(message);
                    }
                } catch (Exception exception) {
                    System.out.println("Error occurred while logging message");
                }
            }

            @Override
            public void flush() {}

            @Override
            public void close() throws SecurityException {}
        };

        logger.addHandler(handler);
        logger.log(Level.INFO, "This is a test message");
    }
}