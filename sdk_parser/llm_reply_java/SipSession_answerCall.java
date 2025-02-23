public class SipSession_answerCall {
    public void test_SipSession_answerCall() {
        SipManager sipManager;
        SipProfile sipProfile;
        SipSession sipSession;

        try {
            sipManager = SipManager.newInstance(this);

            SipProfile.Builder sipProfileBuilder = new SipProfile.Builder("USERNAME", "DOMAIN");
            sipProfileBuilder.setPassword("PASSWORD");
            sipProfile = sipProfileBuilder.build();

            sipManager.open(sipProfile);
            SipManager.Listener sipListener = new SipManager.Listener() {
                @Override
                public void onRinging(SipSession session, SipProfile caller, String sessionDescription) {
                    super.onRinging(session, caller, sessionDescription);
                    try {
                        session.answerCall(sessionDescription, 30);
                    } catch (SipException ex) {
                        ex.printStackTrace();
                    }
                }
            };
            sipSession = sipManager.createSipSession(sipProfile, sipListener);

        } catch (ParseException | SipException ex) {
            ex.printStackTrace();
        }
    }
}