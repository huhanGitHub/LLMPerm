public class Watchable_register {
    public void test_Watchable_register() {
        try {
            // Establish a directory Path
            Path path = Paths.get("/path/to/watched/directory");

            // Establish a WatchService
            WatchService watcher = FileSystems.getDefault().newWatchService();

            // Register the directory with the WatchService using the register() method on the directory instance
            WatchKey watchKey = path.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
            
            while(true) {
                watchKey = watcher.take();

                for (WatchEvent<?> event: watchKey.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    
                    if (kind == OVERFLOW) {
                        System.out.println("Overflow event");
                        continue;
                    }

                    WatchEvent<Path> pathEvent = (WatchEvent<Path>) event;
                    Path filename = pathEvent.context();
                    
                    if (kind == ENTRY_CREATE) {
                        System.out.println("A new file: " + filename + " was created.");
                    } else if (kind == ENTRY_DELETE) {
                        System.out.println("A file: " + filename + " was deleted.");
                    } else if (kind == ENTRY_MODIFY) {
                        System.out.println("A file: " + filename + " was modified.");
                    }
                }
                
                boolean valid = watchKey.reset();
                if (!valid) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}