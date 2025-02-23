public class MbmsDownloadSession_requestUpdateFileServices {
    public void test_MbmsDownloadSession_requestUpdateFileServices() {
        MbmsDownloadSession session = null;
        try {
            session = MbmsDownloadSession.create(this,
                new MbmsDownloadSession.SessionStateCallback() {
                    @Override
                    public void onSessionStateUpdated(int state) {
                        // handle state update here
                    }

                    @Override
                    public void onError(int errorCode, String message) {
                        // handle session error here
                    }
                }, "test_MbmsDownloadSession_requestUpdateFileServices");

            List<String> serviceClasses = new ArrayList<>();
            serviceClasses.add("serviceClass1");
            serviceClasses.add("serviceClass2");
            serviceClasses.add("serviceClass3");
            
            session.requestUpdateFileServices(serviceClasses);
                  
        } catch (IllegalArgumentException e) {
            // handle exception here
        } catch (IllegalStateException e) {
            // handle exception here
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}