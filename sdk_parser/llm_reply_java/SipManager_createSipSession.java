public class SipManager_createSipSession {
    private SipManager sipManager = null;
    private SipProfile sipProfile = null;
    private SipSession sipSession = null;

    public void test_SipManager_createSipSession() {
        try {
            // Initialize the SipManager.
            if (sipManager == null) {
                sipManager = SipManager.newInstance(this);
            }

            // Set up the SipProfile.
            SipProfile.Builder builder = new SipProfile.Builder("user_name", "domain.com");
            builder.setPassword("password");
            sipProfile = builder.build();

            // Create a SipSession.
            sipSession = sipManager.createSipSession(sipProfile, null);

            if (sipSession.getState() == SipSession.State.READY_TO_CALL) {
                System.out.println("SipSession was successfully created and ready to call.");
            }

        } catch (Exception e) {
            System.out.println("Failed to create SipSession: " + e.toString());
        }
    }
}