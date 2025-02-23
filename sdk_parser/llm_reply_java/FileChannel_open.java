public class FileChannel_open {
    public void test_FileChannel_open() throws IOException {
        String filename = "test.txt";
        String testInput = "Test data for FileChannel.";

        // Open a file channel in write mode
        RandomAccessFile file = new RandomAccessFile(filename, "rw");
        FileChannel writeFileChannel = file.getChannel();

        // Prepare a buffer to write to the file
        ByteBuffer buffer = ByteBuffer.allocate(64);
        buffer.clear();
        buffer.put(testInput.getBytes());
        buffer.flip();

        // Write to the file
        while(buffer.hasRemaining()) {
            writeFileChannel.write(buffer);
        }

        // Close the file
        writeFileChannel.close();
        file.close();

        // Now, open the file in read mode
        file = new RandomAccessFile(filename, "r");
        FileChannel readFileChannel = file.getChannel();

        // Prepare a buffer to read back the file
        buffer = ByteBuffer.allocate(64);
        buffer.clear();

        // Read back the file
        readFileChannel.read(buffer);
        
        // Convert the buffer into a string
        String result = new String(buffer.array()).trim();

        // Make sure the content written matches read back
        if (!result.equals(testInput)) {
            System.out.println("File write/read test failed: Content mismatch");
        }

        // Close read file channel
        readFileChannel.close();
        file.close();
    }
}