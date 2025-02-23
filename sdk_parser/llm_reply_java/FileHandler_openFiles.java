public class FileHandler_openFiles {
    public void test_FileHandler_openFiles() {
        Logger logger = Logger.getLogger("MyLog");
        FileHandler fh;

        try {
            // The log file will be located in your application's private data folder. 
            // For this example, you may need to add permissions for writing to external storage.
            String filePath = this.getApplicationContext().getFilesDir().getPath().toString() + "/LogFile.log";
            fh = new FileHandler(filePath);

            // Set the logger to use the File Handler
            logger.addHandler(fh);

            // Optional, removes the default Console Handler
            logger.setUseParentHandlers(false);

            // Set the logger level to control what messages will be logged
            logger.setLevel(Level.ALL);

            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter); 

            // Log an INFO message
            logger.info("Logging started");

        } catch (SecurityException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, "Security exception", e);
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, "IO exception", e);
        }
    }
}