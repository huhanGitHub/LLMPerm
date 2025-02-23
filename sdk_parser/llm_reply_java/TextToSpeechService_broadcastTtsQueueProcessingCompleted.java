public class TextToSpeechService_broadcastTtsQueueProcessingCompleted {
    public void test_TextToSpeechService_broadcastTtsQueueProcessingCompleted() {
        if (mBound) {
            // Call a method from the TextToSpeechService.
            // Assume we want to get TextToSpeech Engine and speak "Hello World"
            TextToSpeech tts = mService.getTts();
            tts.speak("Hello, World!", TextToSpeech.QUEUE_FLUSH, null,"id1");
            mService.broadcastTtsQueueProcessingCompleted();
        }
    }
}