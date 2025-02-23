public class SipManager_isRegistered {

    public void test_SipManager_isRegistered() throws Exception {
        SipManager mSipManager = SipManager.newInstance(this);

        if (mSipManager == null) {
            throw new Exception("SIP Manager is null");
        }

        SipProfile.Builder builder = new SipProfile.Builder("username", "domain");
        builder.setPassword("password");
        SipProfile mSipProfile = builder.build();

        mSipManager.open(mSipProfile);

        mSipManager.setRegistrationListener(mSipProfile.getUriString(), new SipRegistrationListener() {
            @Override
            public void onRegistering(String localProfileUri) {
                System.out.println("Registering with SIP Server...");
            }

            @Override
            public void onRegistrationDone(String localProfileUri, long expiryTime) {
                System.out.println("Registration successful!");
            }

            @Override
            public void onRegistrationFailed(String localProfileUri, int errorCode, String errorMessage) {
                System.out.println("Registration failed.  Please check settings.");
            }
        });
    }
}