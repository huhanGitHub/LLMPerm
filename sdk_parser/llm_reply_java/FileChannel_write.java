public class FileChannel_write {
    public static void test_FileChannel_write() {
        FileOutputStream fos = null;
        FileChannel channel = null;

        try {
            String msg = "Message from test_FileChannel_write";
            byte[] bytes = msg.getBytes();

            String filePath = Environment.getExternalStorageDirectory().getPath() + "/testFile.txt";
            fos = new FileOutputStream(filePath);
            channel = fos.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.clear();
            buffer.put(bytes);

            buffer.flip();
            while(buffer.hasRemaining()) {
                channel.write(buffer);
            }

            Log.i("TestFileChannelWrite", "Message has been written to " + filePath);
        } catch (IOException e) {
            Log.e("TestFileChannelWrite", "Error Writing Message", e);
        } finally {
            try {
                if (channel != null) {
                    channel.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                Log.e("TestFileChannelWrite", "Error Closing Channel/FileOutputStream", e);
            }
        }
    }
}