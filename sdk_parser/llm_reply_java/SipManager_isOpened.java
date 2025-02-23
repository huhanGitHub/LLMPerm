public class SipManager_isOpened {
    public void test_SipManager_isOpened() {
        // Check if device supports SIP connectivity
        if (!SipManager.isApiSupported(this)) {
            return;
        }
        if (!SipManager.isVoipSupported(this)) {
            return;
        }
        // If SIP API is supported, try to create a SIP Manager
        SipManager sipManager = SipManager.newInstance(this);
        try {
            // Create SIP profile
            SipProfile.Builder builder = new SipProfile.Builder("username", "domain");
            builder.setPassword("password");
            SipProfile sipProfile = builder.build();

            // Try to open an SIP Profile
            sipManager.open(sipProfile);
            if(sipManager.isOpened(sipProfile.getUriString())) {
                // SipManager is open and ready to make SIP calls
                //TODO: perform your action here
            }
        } catch (SipException e) {
            e.printStackTrace();
        }
    }
}