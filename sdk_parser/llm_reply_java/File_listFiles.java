public class File_listFiles {
    private void test_File_listFiles() {
        File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        File[] files = directory.listFiles();

        for (File file : files) {
            Log.d("test_File_listFiles", "File: " + file.getName());
        }
    }
}