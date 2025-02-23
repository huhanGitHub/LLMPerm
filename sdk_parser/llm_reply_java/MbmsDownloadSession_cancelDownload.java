public class MbmsDownloadSession_cancelDownload {
    public void test_MbmsDownloadSession_cancelDownload() {
        // Assuming we have the context of your current activity
        Context context = this;

        // Initializing a DownloadRequest object. 
        // In actual usage, this would usually be obtained by setting various parameters on it.
        DownloadRequest downloadRequest = new DownloadRequest.Builder().build();

        // Getting an instance of MbmsDownloadSession
        MbmsDownloadSession session = MbmsDownloadSession.create(context);

        if (session != null) {
            // Cancelling download.
            // Note: In your actual usage, ensure appropriate error handling
            try {
                session.cancelDownload(downloadRequest);
            } catch (MbmsException e) {
                // Handle exception here
            } finally {
                // It's important to always call `session.close()` when done to free up system resources.
                session.close();
            }
        }
    }
}