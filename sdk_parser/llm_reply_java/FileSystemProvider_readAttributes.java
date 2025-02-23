public class FileSystemProvider_readAttributes {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void test_FileSystemProvider_readAttributes() {
        try {
            // Get the default FileSystem
            FileSystem fs = FileSystems.getDefault();

            // Get the FileSystemProvider
            FileSystemProvider provider = fs.provider();

            // Create a Path object
            Path path = Paths.get("/sdcard/photos");

            // Read all attributes as a bulk operation
            BasicFileAttributes attr = provider.readAttributes(path, BasicFileAttributes.class);

            // Print file attributes
            System.out.println("Creation time: " + attr.creationTime());
            System.out.println("Last modified time: " + attr.lastModifiedTime());
            System.out.println("Size: " + attr.size());
            System.out.println("Is directory: " + attr.isDirectory());
            System.out.println("Is regular file: " + attr.isRegularFile());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}