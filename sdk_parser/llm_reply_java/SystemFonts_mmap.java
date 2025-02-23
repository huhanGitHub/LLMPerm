public class SystemFonts_mmap {
    public void test_SystemFonts_mmap() {
        try {
            // Get a handle to a system file
            FileInputStream fileInputStream = new FileInputStream("/system/fonts/Roboto-Regular.ttf");

            // Create a channel of the file
            FileChannel fileChannel = fileInputStream.getChannel();

            // Map the entire font file in to memory
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());

            // Create a new system font with the buffer
            SystemFonts.map(mappedByteBuffer);

            // Asserts a condition, if it fails it throws an AssertionError with the given message.
            assertNotNull("Font could not be mapped", 
                           SystemFonts.map(mappedByteBuffer));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}