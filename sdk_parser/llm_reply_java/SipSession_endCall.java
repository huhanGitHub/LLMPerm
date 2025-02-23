public class SipSession_endCall {
    public void test_SipSession_endCall() {
        //Create a SipManager instance
        SipManager manager = null;
        //Create a SipSession instance
        SipSession session = null;

        //Replace with your SIP account details
        String username = "your_username";
        String domain = "your_domain";
        String password = "your_password";

        try {
            manager = SipManager.newInstance(this);

            SipProfile.Builder builder = new SipProfile.Builder(username, domain);
            builder.setPassword(password);
            SipProfile profile = builder.build();

            SipSession.Listener listener = new SipSession.Listener(){
                @Override
                public void onCalling(SipSession session) {
                    // This method is called when the session is in the process of making a call
                    super.onCalling(session);
                }

                @Override
                public void onCallEnded(SipSession session) {
                    // This method is called when a call ends.
                    super.onCallEnded(session);
                }
            };

            session = manager.createSipSession(profile, listener);

            // After a call is established, you can end it using endCall.
            session.endCall();
        } catch (SipException e) {
            e.printStackTrace();
        }
    }
}