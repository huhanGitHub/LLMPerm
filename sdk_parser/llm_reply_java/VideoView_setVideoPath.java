public class VideoView_setVideoPath {
    public void test_VideoView_setVideoPath(){
        String videoPath = "http://your-url.com/your-video.mp4";

        // Initialize VideoView
        VideoView videoView = (VideoView) findViewById(R.id.video_view);

        // Set the Video URI
        videoView.setVideoPath(videoPath);

        // Create MediaController
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        // Set MediaController for VideoView
        videoView.setMediaController(mediaController);

        // Prepare the video
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // Start the VideoView
                videoView.start();
            }
        });
    }
}