public class FileOwnerAttributeView_setOwner {
    public void test_FileOwnerAttributeView_setOwner(String filePath) {
        // Get the path of the file
        Path path = FileSystems.getDefault().getPath(filePath);
        
        // Get FileOwnerAttributeView of the file
        FileOwnerAttributeView fileOwnerAttributeView = java.nio.file.Files.getFileAttributeView(path, FileOwnerAttributeView.class);
        
        // Fetch UserPrincipalLookupService
        UserPrincipalLookupService userPrincipalLookupService = FileSystems.getDefault().getUserPrincipalLookupService();
        
        try { 
            // Fetch UserPrincipal of the owner
            UserPrincipal userPrincipal = userPrincipalLookupService.lookupPrincipalByName("new_owner");
            
            // Set new owner to the file
            fileOwnerAttributeView.setOwner(userPrincipal);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}