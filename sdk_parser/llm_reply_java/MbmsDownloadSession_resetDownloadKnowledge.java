public class MbmsDownloadSession_resetDownloadKnowledge {
    public void test_MbmsDownloadSession_resetDownloadKnowledge() {
        // Initialize a context, here we use `this` as activity itself act as a Context.
        Context context = this;

        // Initialize an instance of `MbmsDownloadSession` through `create` method.
        MbmsDownloadSession downloadSession = MbmsDownloadSession.create(context, new HandlerExecutor(new Handler(Looper.myLooper())), null /* callback */);
        try {
            // Call `resetDownloadKnowledge` method, for now we're assuming it is going to be executed in main thread.
            downloadSession.resetDownloadKnowledge(serviceId);

        } catch (RuntimeException re) {
            // Handle the potential runtime exception being thrown.
            Log.e("LOG_TAG", "Exception thrown in test_MbmsDownloadSession_resetDownloadKnowledge: ", re);
        } catch (IOException ie) {
            // Handle the potential IO exception being thrown.
            Log.e("LOG_TAG", "Exception thrown in test_MbmsDownloadSession_resetDownloadKnowledge: ", ie);
        } finally {
            // Always close the `MbmsDownloadSession` when you're done with it.
            downloadSession.close();
        }
    }
}