public class MidiManager_openBluetoothDevice {
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void test_MidiManager_openBluetoothDevice() {
        // Get the MidiManager system service
        MidiManager midiManager = (MidiManager) getSystemService(Context.MIDI_SERVICE);

        // Let's assume that we have already paired a Bluetooth MIDI device, and the device's BluetoothDevice object is bluetoothDevice
        BluetoothDevice bluetoothDevice = ...; // You would get this from Android's Bluetooth APIs

        // This is the callback we will use when attempting to open the Bluetooth MIDI device
        MidiManager.OnDeviceOpenedListener callback = new MidiManager.OnDeviceOpenedListener() {
            @Override
            public void onDeviceOpened(MidiDevice device) {
                if (device == null) {
                    Log.e("test", "Could not open device");
                } else {
                    Log.i("test", "Device opened, now we can use it");
                    // Here you might want to save the device object for later use,
                    // or you might want to start using the device
                }
            }
        };

        // We now attempt to open the Bluetooth MIDI device
        midiManager.openBluetoothDevice(bluetoothDevice, callback, new Handler());
    }
}