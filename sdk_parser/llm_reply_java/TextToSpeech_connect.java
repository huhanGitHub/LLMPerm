public class TextToSpeech_connect {
    private TextToSpeech mTextToSpeech = null;

    private void test_TextToSpeech_connect() {
        mTextToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    mTextToSpeech.setLanguage(Locale.US);
                    mTextToSpeech.speak("Hello, Text to Speech demo here.", TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }
}