public class File_isFile {
    private void test_File_isFile() throws IOException {
        File file = new File(getFilesDir(), "test.txt");
        
        if (file.exists() && file.isFile()) {
            Toast.makeText(this, "The file is present.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "This is not a file or the file does not exist.", Toast.LENGTH_SHORT).show();
        }
    }
}