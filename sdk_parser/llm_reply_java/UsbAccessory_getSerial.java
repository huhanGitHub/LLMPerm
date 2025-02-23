public class UsbAccessory_getSerial {
    public void test_UsbAccessory_getSerial() {
        // Get the UsbManager from Android.
        UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

        // Get the list of connected UsbAccessories.
        UsbAccessory[] accessoryList = usbManager.getAccessoryList();

        // If there are any accessories connected.
        if(accessoryList != null) {
            for(UsbAccessory accessory : accessoryList) {
                // Get and print the accessory's serial number.
                String serial = accessory.getSerial();
                Log.i("USB_ACCESSORY_SERIAL", "Accessory's serial: " + serial);
            }
        } else {
            Log.i("USB_ACCESSORY_SERIAL", "No USB accessories found.");
        }
    }
}