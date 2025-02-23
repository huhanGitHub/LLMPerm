public class FileHandler_open {
    public void test_FileHandler_open() {
        // The path to the file
        String filePath = ContextCompat.getExternalFilesDirs(this, null)[0]
                            + File.separator + "myApplication.log";

        FileHandler fileHandler;

        try {
            // Create a new file handler which appends logs to the specified file
            fileHandler = new FileHandler(filePath, true);

            // Obtain the global logger
            Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

            // Set the handler for the global logger
            logger.addHandler(fileHandler);

            // Log a simple INFO message
            logger.info("This is an info log message.");

        } catch (IOException e) {
            // Handle exception
            e.printStackTrace();
        }
    }
}