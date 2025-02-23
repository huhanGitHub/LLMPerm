public class TextToSpeech_speak {
    private TextToSpeech tts;

    // Example of a TextToSpeech method
    public void test_TextToSpeech_speak(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}