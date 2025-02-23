public class MbmsStreamingSession_requestUpdateStreamingServices {
    private void test_MbmsStreamingSession_requestUpdateStreamingServices() {
        Context context = this;
        Executor executor = Executors.newSingleThreadExecutor();
        String serviceClass = "";  // The service class, which should be given by carrier or service provider

        MbmsStreamingSession.Callback callback = new MbmsStreamingSession.Callback() {
            @Override
            public void onError(int errorCode, String message) {
                Log.e("MbmsStreamingSession", "Error requesting streaming service update: " + errorCode + ", " + message);
            }
        };

        MbmsStreamingSession session = MbmsStreamingSession.create(context, executor, callback);

        session.requestUpdateStreamingServices(serviceClass);

        // Make sure to close session when appropriate to release resources
    }
}