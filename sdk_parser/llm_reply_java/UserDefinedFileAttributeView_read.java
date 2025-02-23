public class UserDefinedFileAttributeView_read {

    @SuppressLint("NewApi")
    public void test_UserDefinedFileAttributeView_read() {
        Path file = Paths.get("/path/to/your/file");
        UserDefinedFileAttributeView view = Files.getFileAttributeView(file, UserDefinedFileAttributeView.class);

        if (view != null) {
            try {
                // List user-defined attributes
                List<String> attributes = view.list();
                for (String attribute : attributes) {
                    // Allocate a ByteBuffer
                    ByteBuffer buffer = ByteBuffer.allocate(view.size(attribute));

                    // Read the value of the attribute into the buffer
                    view.read(attribute, buffer);

                    // Prepare the buffer for reading
                    buffer.flip();

                    // Convert to string (assuming the attribute value is a string)
                    String value = StandardCharsets.UTF_8.decode(buffer).toString();

                    Log.d("Attribute_Value", "Attribute: " + attribute + ", Value: " + value);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("UDFAV_Test", "UserDefinedFileAttributeView is not supported on this file system.");
        }
    }
}