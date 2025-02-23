public class TextToSpeechService_playAudio {
    private void test_TextToSpeechService_playAudio(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }
}