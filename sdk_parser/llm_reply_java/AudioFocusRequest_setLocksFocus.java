public class AudioFocusRequest_setLocksFocus {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void test_AudioFocusRequest_setLocksFocus() {
        // Step 1: Create an AudioManager instance
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Step 2: Create an AudioFocusRequest instance
        AudioFocusRequest audioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
            .setAudioAttributes(new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build())
            .setAcceptsDelayedFocusGain(true)
            .setOnAudioFocusChangeListener(new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    switch (focusChange) {
                        case AudioManager.AUDIOFOCUS_GAIN:
                            // Audio Focus has been gained
                            break;
                        case AudioManager.AUDIOFOCUS_LOSS:
                            // Audio Focus has been lost
                            break;
                        default:
                            // The given focusChange value does not match any known audio focus state.
                            break;
                    }
                }
            }).setWillPauseWhenDucked(true).build();

        // Step 3: Use the setLocksFocus method to set the focus lock
        // Please note that this method has been deprecated in Android Q
        audioFocusRequest.setLocksFocus(true);

        // Step 4: Request audio focus
        int result = audioManager.requestAudioFocus(audioFocusRequest);

        // Step 5: Check the result 
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            // The audio focus was successfully requested
        } else {
            // The audio focus was not successfully requested
        }
    }
}