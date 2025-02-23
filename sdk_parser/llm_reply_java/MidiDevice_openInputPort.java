import android.content.Context;
import android.media.midi.MidiDeviceInfo;
import android.media.midi.MidiDevice;
import android.media.midi.MidiManager;
import android.os.Bundle;
import android.util.Log;

public class MidiDevice_openInputPort {
    public void test_MidiDevice_openInputPort() {
        MidiManager midiManager = (MidiManager) getSystemService(Context.MIDI_SERVICE);
        MidiDeviceInfo[] infos = midiManager.getDevices();

        if (infos.length == 0) {
            Log.d("MidiTest", "No MIDI devices found");
            return;
        }

        midiManager.openDevice(infos[0], new MidiManager.OnDeviceOpenedListener() {
            @Override
            public void onDeviceOpened(MidiDevice device) {
                if (device == null) {
                    Log.d("MidiTest", "Could not open device");
                    return;
                }

                MidiDevice.MidiConnection connection;
                try {
                    connection = device.openInputPort(0);
                    if (connection == null) {
                        Log.d("MidiTest", "Could not open input port");
                        return;
                    }
                    Log.d("MidiTest", "Input port opened successfully");
                } catch (Exception e) {
                    Log.d("MidiTest", "Exception: " + e);
                }
            }
        }, null);
    }
}