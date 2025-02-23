public class FileSystemProvider_checkAccess {
    
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void test_FileSystemProvider_checkAccess() {
        try {
            Path path = Paths.get("/example/path/to/file.txt");
            FileSystemProvider provider = path.getFileSystem().provider();
    
            // Check whether the file is readable, writable, and executable
            provider.checkAccess(path, AccessMode.READ, AccessMode.WRITE, AccessMode.EXECUTE);
            Toast.makeText(this, "File can be accessed!", Toast.LENGTH_SHORT).show();
        } catch (IOException | UnsupportedOperationException e) {
            Toast.makeText(this, "Cannot access the file! " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}