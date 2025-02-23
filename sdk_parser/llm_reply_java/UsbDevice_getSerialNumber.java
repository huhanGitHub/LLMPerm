public class UsbDevice_getSerialNumber {

    import android.content.Context;
    import android.hardware.usb.UsbDevice;
    import android.hardware.usb.UsbManager;
    import android.os.Build;

    import java.util.HashMap;

    public void test_UsbDevice_getSerialNumber(Context context) {
        UsbManager manager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        
        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
        for (UsbDevice device : deviceList.values()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String serialNumber = device.getSerialNumber();
                // Use your serial number here
            } 
        }
    }
}