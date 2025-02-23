public class VisualVoicemailService_setSmsFilterSettings extends VisualVoicemailService {
    private static final String TAG = VisualVoicemailService_setSmsFilterSettings.class.getSimpleName();

    public void test_VisualVoicemailService_setSmsFilterSettings() {
        VisualVoicemailSmsFilterSettings settings = new VisualVoicemailSmsFilterSettings.Builder()
           .setClientPrefix("example")
           .setOriginatingNumbers(Arrays.asList("123", "456"))
           .build();

        setSmsFilterSettings(settings);
        Log.d(TAG, "Visual voicemail SMS filter settings set: " + settings.toString());
    }
}