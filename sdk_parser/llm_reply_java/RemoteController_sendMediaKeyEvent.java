public class RemoteController_sendMediaKeyEvent {

    public void test_RemoteController_sendMediaKeyEvent() {
        // create a new RemoteController
        Context context = this;
        RemoteController remoteController = new RemoteController(context, new RemoteController.OnClientUpdateListener() {
            @Override
            public void onClientChange(boolean clearing) {
                // handle the event
            }

            @Override
            public void onClientPlaybackStateUpdate(int state) {
                // handle the event
            }

            @Override
            public void onClientPlaybackStateUpdate(int state, long stateChangeTimeMs, long currentPosMs, float speed) {
                // handle the event
            }

            @Override
            public void onClientTransportControlUpdate(int transportControlFlags) {
                // handle the event
            }

            @Override
            public void onClientMetadataUpdate(RemoteController.MetadataEditor metadataEditor) {
                // handle the event
            }
        });

        // set the remote control's media player to be the system's media player
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.registerRemoteController(remoteController);

        // send a pause key event to the media player
        KeyEvent pauseEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PAUSE);
        remoteController.sendMediaKeyEvent(pauseEvent);

        // send a play key event to the media player
        KeyEvent playEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY);
        remoteController.sendMediaKeyEvent(playEvent);
    }
}