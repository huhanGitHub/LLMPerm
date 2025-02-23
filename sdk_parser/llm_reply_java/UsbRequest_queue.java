import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbRequest;

import java.nio.ByteBuffer;

public class UsbRequest_queue {

    public void test_UsbRequest_queue() {
        // Creates a new UsbRequest object.
        UsbRequest usbRequest = new UsbRequest();

        // Initialize this UsbRequest so it can perform I/O operations in the given endpoint.
        usbRequest.initialize(deviceConnection, endpoint);

        // Prepare a ByteBuffer to be sent.
        ByteBuffer buffer = ByteBuffer.allocate(100);
        buffer.put("Test Data".getBytes());

        // Queues a request to send the data contained in the buffer to an endpoint.
        // The direction of the endpoint must be UsbConstants.USB_DIR_OUT.
        boolean result = usbRequest.queue(buffer, buffer.capacity());

        // Show an information message
        if (result) {
            System.out.println("The request has been successfully queued");
        } else {
            System.out.println("Failed to queue the request");
        }

        // Remember to close your UsbRequest and release all its resources!
        usbRequest.close();
    }
}