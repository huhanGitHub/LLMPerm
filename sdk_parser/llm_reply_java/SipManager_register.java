public class SipManager_register {
    public void test_SipManager_register() {
        SipManager sipManager = null;
        SipProfile sipProfile = null;

        try {
            // Check if SIP VoIP is supported on this device.
            if(SipManager.isVoipSupported(this) && SipManager.isApiSupported(this)) {

                sipManager = SipManager.newInstance(this);

                SipProfile.Builder builder = new SipProfile.Builder("username", "domain");
                builder.setPassword("password");
                sipProfile = builder.build();

                // Register the profile.
                sipManager.open(sipProfile);
            }
        } catch (SipException e) {
            // Handle exception e.g., SIP is not supported or internet permission not granted.
            e.printStackTrace();
        } finally {
            // Don't forget to close the SipProfile and SipManager to release resources. 
            if (sipProfile != null) {
                try {
                    sipManager.close(sipProfile.getUriString());
                } catch (SipException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}