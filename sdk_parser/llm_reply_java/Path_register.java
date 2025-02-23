public class Path_register {
    public void test_Path_register() throws IOException, InterruptedException {
        // Convert the string path to a Path object
        Path path = Paths.get("/path/to/your/directory");
        
        // Get the file system's watch service
        WatchService watchService = FileSystems.getDefault().newWatchService();
        
        // Register the path with the watch service for CREATE, DELETE and MODIFY events
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, 
                      StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
        
        // Start an infinite loop
        while (true) {
            // Retrieve and remove the next watch key
            WatchKey key = watchService.take();
            
            // Get the list of pending events for the watch key
            for (WatchEvent<?> watchEvent : key.pollEvents()) {
                // Get the kind of event
                WatchEvent.Kind<?> kind = watchEvent.kind();
                
                if (kind == StandardWatchEventKinds.OVERFLOW) {
                    continue;
                }
                
                // Get the filename for the event
                WatchEvent<Path> watchEventPath = (WatchEvent<Path>) watchEvent;
                Path filename = watchEventPath.context();
                
                // Print out the kind of event and the filename
                Log.d("MainActivity", kind + ": " + filename);
            }
            
            // Reset the watch key
            boolean valid = key.reset();
            
            // If the watch key is no longer valid, then exit the loop
            if (!valid) {
                break;
            }
        }    
    }
}