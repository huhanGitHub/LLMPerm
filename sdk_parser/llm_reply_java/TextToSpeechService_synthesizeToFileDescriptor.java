import android.os.ParcelFileDescriptor;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class TextToSpeechService_synthesizeToFileDescriptor extends AppCompatActivity {
    private TextToSpeech textToSpeech;

    @SuppressLint("NewApi")
    public void test_TextToSpeechService_synthesizeToFileDescriptor() {
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);

                    String myText1 = "Hello World. I'm your Text To Speech Engine.";
                    File file = new File(getExternalFilesDir("tts"), "myText1.wav");

                    try {
                        file.createNewFile();
                        ParcelFileDescriptor fileDescriptor =
                                ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_WRITE);

                        textToSpeech.synthesizeToFile(myText1, null, fileDescriptor.getFileDescriptor(), "tts_1");
                        fileDescriptor.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }

        super.onDestroy();
    }
}