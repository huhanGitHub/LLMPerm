import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.content.Context;
import android.util.Log;
import java.util.Locale;

public class TextToSpeechService_getSecureSettingInt {

    public void test_TextToSpeechService_getSecureSettingInt(Context context) {
        TextToSpeech tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.UK);

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    } else {
                        int defaultPitch = Settings.Secure.getInt(
                            context.getContentResolver(),
                            Settings.Secure.TTS_DEFAULT_PITCH, 
                            100);
                        int defaultRate = Settings.Secure.getInt(
                            context.getContentResolver(), 
                            Settings.Secure.TTS_DEFAULT_RATE, 
                            100);

                        tts.setPitch(defaultPitch / 100.0f);
                        tts.setSpeechRate(defaultRate / 100.0f);

                        speakOut(tts, "Testing TextToSpeech service");
                    }

                } else {
                    Log.e("TTS", "Initialization Failed!");
                }
            }
        });
    }

    private void speakOut(TextToSpeech tts, String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "");
    }
}