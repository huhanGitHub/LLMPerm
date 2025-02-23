public class TvRecordingClient_pauseRecording {
    private TvRecordingClient mTvRecordingClient;
    
    public void test_TvRecordingClient_pauseRecording() {
        try {
            mTvRecordingClient.pauseRecording();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}