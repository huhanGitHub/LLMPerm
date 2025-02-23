public class MidiManager_openDevice {
    public void test_MidiManager_openDevice() {
        // Acquiring MidiManager instance
        MidiManager midiManager = (MidiManager) getSystemService(Context.MIDI_SERVICE);

        // Retrieve list of connected devices
        MidiDeviceInfo[] midiDevices = midiManager.getDevices();
        if (midiDevices.length == 0) {
            // Display a message if no devices found
            Log.d("No MIDI Devices", "No MIDI devices could be retrieved");
            return;
        }

        // Get the first MIDI device information object
        MidiDeviceInfo firstDevice = midiDevices[0];
        
        // Attempt to open the first MIDI device
        midiManager.openDevice(firstDevice, new MidiManager.OnDeviceOpenedListener() {
            @Override
            public void onDeviceOpened(MidiDevice midiDevice) {
                if (midiDevice == null) {
                    // If the device could not be opened properly
                    Log.d("MIDI Device Error", "Could not open MIDI device");
                } else {
                    // If the device was opened, you can now send and receive MIDI data
                    Log.d("MIDI Device Success", "MIDI device opened successfully!");
                }
            }
        }, new Handler(Looper.getMainLooper()));
    }
}