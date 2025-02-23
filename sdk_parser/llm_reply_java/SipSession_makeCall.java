public class SipSession_makeCall {

    public void test_SipSession_makeCall(SipManager manager, SipProfile localProfile) {
        try {
            // Prepare the SIP session
            SipSession.Listener listener = new SipSession.Listener(){
                @Override
                public void onCalling(SipSession session) {
                        super.onCalling(session);
                }

                @Override
                public void onError(SipSession session, int error, String message) {
                        super.onError(session, error, message);
                }

                @Override
                public void onRegistered(SipSession session) {
                        super.onRegistered(session);
                }

                @Override
                public void onRinging(SipSession session, SipProfile caller, String sessionDescription) {
                        super.onRinging(session, caller, sessionDescription);
                }

                @Override
                public void onRingingBack(SipSession session) {
                        super.onRingingBack(session);
                }

                @Override
                public void onRegistrationFailed(SipSession session, int errorCode, String message) {
                        super.onRegistrationFailed(session, errorCode, message);
                }

                @Override
                public void onRegistrationTimeout(SipSession session) {
                        super.onRegistrationTimeout(session);
                }
            };

            SipSession session = manager.createSipSession(localProfile, listener);
            
            // Make a call
            String targetSipAddress = "sip:username@domain.com";
            SipProfile targetProfile = new SipProfile.Builder(targetSipAddress).build();
            int timeout = 30;
            session.makeCall(targetProfile, null, timeout);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}