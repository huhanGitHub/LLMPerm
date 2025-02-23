public class AsynchronousFileChannel_open {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void test_AsynchronousFileChannel_open() {
        File file = new File(getFilesDir(), "test.txt");

        try {
            // Create a new file and write some data
            if (!file.exists()) {
                FileOutputStream out = openFileOutput("test.txt", MODE_PRIVATE);
                out.write("Hello World!".getBytes());
                out.close();
            }

            // Open existing file for read and write operations
            AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel.open(
                    Paths.get(file.getAbsolutePath()),
                    StandardOpenOption.READ, StandardOpenOption.WRITE);

            // Use different asynchronous methods to read, write operations

            // Close channel
            asynchronousFileChannel.close();

        } catch (IOException e) {
            Log.e("Test", "IO Exception", e);
        }
    }
}