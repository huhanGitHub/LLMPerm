public class Files_newOutputStream {
    public void test_Files_newOutputStream() {
        Path path = Paths.get(getFilesDir(), "MyFile.txt");
        String textToWrite = "Hello World!";

        try (OutputStream outputStream = Files.newOutputStream(path)) {
            outputStream.write(textToWrite.getBytes());
            Log.d("test_Files_newOutputStream", "Successfully written to the file");
        } catch (IOException e) {
            Log.e("test_Files_newOutputStream", "Failed to write to the file", e);
        }
    }
}