public class UsbRequest_queueIfConnectionOpen {
    public void test_UsbRequest_queueIfConnectionOpen() {
        UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        PendingIntent permissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        UsbDevice usbDevice; // must be initialized

        if (usbManager.hasPermission(usbDevice)) {
            UsbInterface usbInterface; // must be initialized
            UsbDeviceConnection connection = usbManager.openDevice(usbDevice);
            UsbEndpoint endpoint = usbInterface.getEndpoint(0); // must be properly initialized to a valid endpoint
            UsbRequest usbRequest = new UsbRequest();
            usbRequest.initialize(connection, endpoint);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            boolean requestQueued = usbRequest.queue(buffer, buffer.capacity());

            if (requestQueued)
                Toast.makeText(this, "USB connection open and data queued successfully", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Failed to queue data over USB connection", Toast.LENGTH_SHORT).show();

            connection.close();
        } else {
            Toast.makeText(this, "Cannot open USB connection without permission", Toast.LENGTH_SHORT).show();
        }
    }
}