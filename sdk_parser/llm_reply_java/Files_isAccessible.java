public class Files_isAccessible {
    public boolean test_Files_isAccessible(String filePath) {
        File file = new File(filePath);
        return file.canRead();
    }
}