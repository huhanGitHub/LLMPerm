public class MidiDeviceService_onCreate {
    public void test_MidiDeviceService_onCreate() {
        Intent intent = new Intent(this, MyMidiDeviceService.class);
        startService(intent);
    }
}