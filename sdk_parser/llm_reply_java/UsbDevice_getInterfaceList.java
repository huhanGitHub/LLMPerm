public class UsbDevice_getInterfaceList {
    public void test_UsbDevice_getInterfaceList() {
        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();

        for (UsbDevice device : deviceList.values()) {

            if (device != null) {
                Log.v(TAG, "Device Name: " + device.getDeviceName());
                Log.v(TAG, "Device ID: " + device.getDeviceId());
                Log.v(TAG, "Device Protocol: " + device.getDeviceProtocol());

                int interfaceCount = device.getInterfaceCount();
                Log.v(TAG, "Device Interface Count: " + interfaceCount);
                for (int i = 0; i < interfaceCount; i++) {
                    UsbInterface usbInterface = device.getInterface(i);
                    Log.v(TAG, "Interface " + i + ": " + usbInterface.toString());
                }
            }
        }
    }
}