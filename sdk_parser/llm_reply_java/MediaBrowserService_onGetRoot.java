public class MediaBrowserService_onGetRoot {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void test_MediaBrowserService_onGetRoot() {
        // Create a mock MediaBrowserService
        MediaBrowserService mediaBrowserService = new MediaBrowserService() {
            @Override
            public BrowserRoot onGetRoot(String clientPackageName, int clientUid, Bundle rootHints) {
                // Check clientPackageName, clientUid and rootHints here.
                // Decide whether the client is allowed to browse the media content.

                // If allowed, return your BrowserRoot for browsing your media content.
                // In real application, this is the place where you decide your top hierarchy level in browsing tree.
                return new BrowserRoot("MyMediaId", null);
            }

            @Override
            public void onLoadChildren(String parentId, 
                    Result<List<MediaBrowserCompat.MediaItem>> result, 
                    Bundle options) {
                // Check parentId here.
                // Based on parentId, return the children for the parent media id.
                // This is the place where you decide the second and lower hierarchy level in browsing tree.
            }
        };

        // Calling onGetRoot method and printing the result
        String clientPackageName = "com.example.app";
        int clientUid = 1234;
        Bundle rootHints = null; // or you can pass your own bundle
        MediaBrowserService.BrowserRoot root = mediaBrowserService.onGetRoot(clientPackageName, clientUid, rootHints);
        Log.d("MediaBrowserService", "Root ID: " + root.getRootId());
    }
}