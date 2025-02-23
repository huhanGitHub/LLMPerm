public class TvRecordingClient_startRecording {

    public void test_TvRecordingClient_startRecording() {
        // Get reference to the system service
        TvInputManager tvInputManager = (TvInputManager) getSystemService(Context.TV_INPUT_SERVICE);

        // Create RecordingCallback and listener to get response
        TvInputManager.RecordingCallback callback = new TvInputManager.RecordingCallback() {
            @Override
            public void onStartRecordingFailed(@Nullable Uri channelUri, int reason) {
                super.onStartRecordingFailed(channelUri, reason);

                // Handle error here
                Log.e("TvRecordingClient", "Start recording failed with reason: " + reason);
            }

            @Override
            public void onRecordingStopped(@Nullable Uri recordedProgramUri) {
                super.onRecordingStopped(recordedProgramUri);

                // Handle the event here
                Log.i("TvRecordingClient", "Recording stopped for program: " + recordedProgramUri);
            }

            @Override
            public void onTuned(@Nullable Uri channelUri) {
                super.onTuned(channelUri);

                // Handle the event here
                Log.i("TvRecordingClient", "Tuned to channel: " + channelUri);
            }
        };

        // Instantiate TvRecordingClient
        TvInputManager.TvRecordingClient recordingClient = 
            new TvInputManager.TvRecordingClient(
                this, tvInputManager, ComponentName.unflattenFromString("yourTvInputService"), 
                callback, Handler.createAsync(Looper.getMainLooper())
            );

        // Start recording
        Uri channelUri = Uri.parse("content://android.media.tv/channel/1");  // Put your channel uri here
        recordingClient.tune(channelUri);
        recordingClient.startRecording(channelUri);
    }
}