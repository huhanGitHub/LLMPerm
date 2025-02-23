import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.content.Context;
import java.util.HashMap;

public class UsbDevice_getDeviceId {

    public void test_UsbDevice_getDeviceId(Context context) {
        UsbManager usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();

        // Iterate over each device in the list
        for (UsbDevice device : deviceList.values()) {
            // Print device ID
            int deviceId = device.getDeviceId();
            System.out.println("Device ID: " + deviceId);
        }
    }
}