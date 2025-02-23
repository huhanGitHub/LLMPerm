public class TextToSpeech_addEarcon {
    public void test_TextToSpeech_addEarcon() {
        TextToSpeech tts = null;
        String earconName  = "earconName";
        String packageName = "packageName";
        int resourceId  = 1000;

        try {
            tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    // Adding earcon
                    int result = tts.addEarcon(earconName, packageName, resourceId);
                
                    if(result == TextToSpeech.SUCCESS) {
                        Log.d("TTS", "Earcon added successfully");
                    } else {
                        Log.d("TTS", "Failed to add earcon");
                    }
                }
                }
            });

        } catch (Exception ex) {
            Log.e("TTS", "Initialization failed");
        } finally {
            if (tts != null) {
                tts.shutdown();
            }
        }
    }
}