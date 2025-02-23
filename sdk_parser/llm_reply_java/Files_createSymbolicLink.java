public class Files_createSymbolicLink {
    private static void test_Files_createSymbolicLink() {
        // Specify the file path
        Path newLink = Paths.get("/tmp/mySymbolicLink");
        Path existingFile = Paths.get("/tmp/myExistingFile");

        try {
            // Check if the symbolic link exists or not
            if (Files.exists(newLink)) {
                // Delete if exists
                Files.delete(newLink);
            }

            // Create the symbolic link
            Files.createSymbolicLink(newLink, existingFile);
            
            // Check if the link 
            if (Files.isSymbolicLink(newLink)) {
                System.out.println("The symbolic link was created successfully!");
            } else {
                System.out.println("Failed to create the symbolic link...");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedOperationException e) {
            // Some file systems do not support symbolic links.
            System.err.println(e);
        }
    }
}