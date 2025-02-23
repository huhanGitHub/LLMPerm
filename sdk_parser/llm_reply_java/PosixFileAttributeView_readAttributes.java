public class PosixFileAttributeView_readAttributes {
    public void test_PosixFileAttributeView_readAttributes() {
        File file = new File("/path/to/your/file");
        
        if (!file.exists()) {
            System.out.println("Please make sure your file exists, and then try again.");
            return;
        }
        Path path = Paths.get(file.toURI());
        
        try {
            PosixFileAttributeView view = Files.getFileAttributeView(path, PosixFileAttributeView.class);
            if(view != null) {
                PosixFileAttributes attributes = view.readAttributes();
                
                System.out.println("File Owner: " + attributes.owner().getName());
                System.out.println("File Permission: " + attributes.permissions().toString());
                System.out.println("File Group: " + attributes.group().getName());
            }
            else {
                System.out.println("PosixFileAttributeView is not supported.");
            }
        } catch (IOException e) {
            System.out.println("Exception while reading file attributes: " + e.getMessage());
        }
    }
}