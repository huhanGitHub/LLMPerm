import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;

public class TextToSpeech_synthesizeToFile {
    public void test_TextToSpeech_synthesizeToFile() {
        TextToSpeech tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                    String textToSpeech = "Hello, this is a test.";
                    String destFile = getExternalFilesDir(null).getAbsolutePath() + "/synthesized_speech.wav";

                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                        @Override
                        public void onStart(String s) {
                        }

                        @Override
                        public void onDone(String s) {
                            File speechFile = new File(destFile);
                            if (speechFile.exists()) {
                            } else {
                            }
                        }

                        @Override
                        public void onError(String s) {
                        }
                    });

                    String utteranceId = "utterance_id";

                    HashMap<String, String> params = new HashMap<>();
                    params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId);

                    tts.synthesizeToFile(textToSpeech, params, destFile);
                }
            }
        });
    }
}