import java.util.logging.*;

public class Handler_checkPermission {
    public void test_Handler_checkPermission() {
        // Create a new handler
        Handler handler = new StreamHandler(System.out, new SimpleFormatter()) {
            @Override
            public void close() {
                super.close();
            }

            @Override
            public void publish(LogRecord record) {
                super.publish(record);
                flush();
            }
        };

        try {
            // Check for permission
            handler.checkAccess();
            System.out.println("Access granted");
        } catch (SecurityException e) {
            System.out.println("No permission to perform this operation");
            e.printStackTrace();
        }
    }
}