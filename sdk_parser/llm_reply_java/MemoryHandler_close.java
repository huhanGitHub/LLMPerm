public class MemoryHandler_close {

    public void test_MemoryHandler_close() throws SecurityException {
        // Assuming that you're logging to a memory file
        Handler targetHandler = new FileHandler("memoryLog.log");
        
        // You create a MemoryHandler with a target of aforementioned FileHandler
        MemoryHandler handler = new MemoryHandler(targetHandler, 1000, Level.ALL);

        // Assume we add this handler to global application logger
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.addHandler(handler);

        // Verify that the handler got added.
        boolean isHandlerAdded = Arrays.asList(logger.getHandlers()).contains(handler);
        if(isHandlerAdded){
            System.out.println("MemoryHandler added successfully!");
        }
          
        // Now we close the handler. Once it is called, the handler shouldn't be used.
        handler.close();
        
        // Let's check if close was successful by trying to add the handler to logger once more
        try {
            logger.addHandler(handler);
            System.out.println("MemoryHandler is still open!");
        } catch (SecurityException e) {
            System.out.println("MemoryHandler is closed successfully!");
        }
    }
}