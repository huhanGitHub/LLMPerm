public class Files_copy {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void test_Files_copy() {
        File file = new File("/path/to/source/file");
        File copiedFile = new File("/path/to/destination/file");

        try {
            Path copied = file.toPath();   //convert file to path
            Path originalPath = copiedFile.toPath(); //convert file to path

            Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}