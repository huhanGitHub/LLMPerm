public class SipManager_getSessionFor {
    public void test_SipManager_getSessionFor() {
        // 1. Initialize the SipManager
        SipManager sipManager = SipManager.newInstance(this);

        // 2. Create a SipProfile
        SipProfile.Builder builder;
        try {
            builder = new SipProfile.Builder("username", "domain");
            builder.setPassword("password");
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        SipProfile sipProfile = builder.build();

        // 3. Open a SIP session
        try {
            sipManager.open(sipProfile);
        } catch (SipException e) {
            e.printStackTrace();
            return;
        }

        // 4. Get the session
        try {
            SipSession sipSession = sipManager.getSessionFor(sipProfile);
            // Here you can do something with the session.
        } catch (SipException e) {
            e.printStackTrace();
        }
    }
}