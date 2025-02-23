public class Files_find {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void test_Files_find() {
        try {
            // Get the path to the external storage directory
            File externalStorage = Environment.getExternalStorageDirectory();
            Path path = externalStorage.toPath();

            // Prepare a file name filter that matches PNG files
            BiPredicate<Path, BasicFileAttributes> matcher =
                    (filePath, fileAttr) -> fileAttr.isRegularFile() && filePath.toString().endsWith(".png");

            // Use Files.find() to find all PNG files in the directory and subdirectories
            try (Stream<Path> stream = Files.find(path, Integer.MAX_VALUE, matcher)) {
                // I'm just printing the files here, replace with your own logic
                stream.forEach(matchedPath -> Log.d("test_Files_find", matchedPath.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (NoSuchFileException e) {
            Log.e("test_Files_find", "Directory not found: " + e.getMessage());
        }
    }
}