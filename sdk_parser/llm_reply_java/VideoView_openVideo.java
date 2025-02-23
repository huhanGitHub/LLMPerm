public class VideoView_openVideo {

    public void test_VideoView_openVideo() {
        
        // Initialize the VideoView. 
        // It assumes that you have a VideoView element in your activity_main.xml file with id 'my_video_view'.
        VideoView videoView = findViewById(R.id.my_video_view);

        // Initialize a URI that represents the video file. 
        // Replace 'my_video' with your desired filename
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.my_video;
        Uri uri = Uri.parse(videoPath);
        
        // Set the video URI
        videoView.setVideoURI(uri);

        // Initialize a new MediaController and assign it to the VideoView
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        
        // Start the VideoView
        videoView.start();
    }
}