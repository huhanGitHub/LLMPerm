public class MediaController_initFloatingWindow {
    public void test_MediaController_initFloatingWindow() {
        // Please replace 'this' with the context if you're not in an activity context.
        VideoView videoView = new VideoView(this);

        // Set the video path here.
        videoView.setVideoPath("your_video_path");

        MediaController mediaController = new MediaController(this);

        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);

        // Here we create a system overlay window for the video view.
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.gravity = Gravity.CENTER;
        params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags |= WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        // Now we initialize a new WindowManager
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);

        // And finally attach the video view to the window manager.
        wm.addView(videoView, params);

        videoView.start();
    }
}