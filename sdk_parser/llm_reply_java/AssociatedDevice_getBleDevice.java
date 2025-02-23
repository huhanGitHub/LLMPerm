public class AssociatedDevice_getBleDevice {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int MY_PERMISSIONS_REQUEST_BLUETOOTH = 2;

    private void test_AssociatedDevice_getBleDevice() {
        // Check for necessary permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.BLUETOOTH},
                    MY_PERMISSIONS_REQUEST_BLUETOOTH);
        }

        // Initialize the AssociatedDevice
        AssociatedDevice associatedDevice = // Please implement if your class has specific constructors or methods to obtain an instance

        // Call getBleDevice method
        BluetoothDevice bleDevice = associatedDevice.getBleDevice();

        // Log the BLE device name and address
        if (bleDevice != null) {
            Log.i("TestTag", "BLE Device Name: " + bleDevice.getName() + ", Address: " + bleDevice.getAddress());
        } else {
            Log.i("TestTag", "Cannot get BLE device, the bleDevice is null");
        }
    }
}