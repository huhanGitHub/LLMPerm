import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

public class AsynchronousFileChannel_write {
    public void test_AsynchronousFileChannel_write() {
        try {
            AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel.open(
                    Paths.get("sample.txt"), StandardOpenOption.WRITE);
            
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            String data = "Testing AsynchronousFileChannel write operation";
            buffer.clear();
            buffer.put(data.getBytes());
            buffer.flip();

            // Start the write
            Future<Integer> operation = asynchronousFileChannel.write(buffer, 0);

            while (!operation.isDone()) {
                // Here we can do something while the write operation is being executed asynchronously
                System.out.println("Waiting for the write operation to complete...");
            }

            // Get the result of the operation (number of bytes written)
            int result = operation.get();
            System.out.println("Bytes written : " + result);

            asynchronousFileChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}