public class FileHandler_close {
    private void test_FileHandler_close() {
        // Define file handler
        FileHandler fileHandler = null;

        // Define logger
        Logger logger = Logger.getLogger(this.getClass().getName());

        try {
            // Create a new file handler
            fileHandler = new FileHandler("myLogFile.log", true);

            // Add it to the logger
            logger.addHandler(fileHandler);

            // Log a simple message
            logger.info("This is just a log message.");

            System.out.println("Log message has been written");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Writing log message failed: " + e.getMessage());

        } finally {
            // It's good practice to close the FileHandler to free up resources
            if (fileHandler != null) {
                // Remove file handler from logger
                logger.removeHandler(fileHandler);

                // Close the file handler
                fileHandler.close();

                System.out.println("File handler level has been closed.");
            }
        }
    }
}