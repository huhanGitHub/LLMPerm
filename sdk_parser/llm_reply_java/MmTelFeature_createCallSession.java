public class MmTelFeature_createCallSession {

    public void test_MmTelFeature_createCallSession(MmTelFeature mmTelFeature, String sessionId, String callId) {
        int mSessionId = Integer.parseInt(sessionId);
        int mCallId = Integer.parseInt(callId);
        mmTelFeature.createCallSession(mSessionId, mCallId); 
    }
}