public class FileSystem_getFileStores {
    public void test_FileSystem_getFileStores() {
        // Get the default FileSystem
        java.nio.file.FileSystem fileSystem = FileSystems.getDefault();
        
        // Iterate over the FileStore objects 
        for (FileStore fileStore : fileSystem.getFileStores()) {
            // Display the name and type of file store
            try {
                System.out.println("name: " + fileStore.name());
                System.out.println("type: " + fileStore.type());
                System.out.println("total space: " + fileStore.getTotalSpace());
                System.out.println("usable space: " + fileStore.getUsableSpace());
            } catch (IOException e) {
                System.out.println("An error occurred while getting the file store details.");
                e.printStackTrace();
            }
        }
    }
}