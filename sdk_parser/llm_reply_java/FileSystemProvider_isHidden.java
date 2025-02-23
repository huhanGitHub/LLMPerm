public class FileSystemProvider_isHidden {
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public boolean test_FileSystemProvider_isHidden(Context context) {
        try {
            // Get the FileSystemProvider
            FileSystemProvider provider = FileSystems.getDefault().provider();

            // Create a Path object
            Uri uri = Uri.parse("file://" + context.getExternalCacheDir().getAbsolutePath() + "/test.txt");
            Path path = Paths.get(uri.getPath());

            // Use isHidden method and return its value
            return provider.isHidden(path);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}