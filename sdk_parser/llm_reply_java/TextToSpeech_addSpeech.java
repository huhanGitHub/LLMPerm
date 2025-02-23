public class TextToSpeech_addSpeech {

    public void test_TextToSpeech_addSpeech() {
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                    String simpleText = "Hello, this is a test of TextToSpeech.";
                    String utteranceId = this.hashCode() + "";
                    textToSpeech.addSpeech(simpleText, utteranceId);
                    textToSpeech.speak(simpleText, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
                }
            }
        });
    }
}