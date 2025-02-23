public class SipManager_makeAudioCall {
    SipManager manager = null;
    SipAudioCall call = null;
    SipProfile profile = null;

    public void test_SipManager_makeAudioCall() {
        try {
            manager = SipManager.newInstance(null);

            SipProfile.Builder builder = new SipProfile.Builder("USERNAME", "DOMAIN");
            builder.setPassword("PASSWORD");
            profile = builder.build();

            manager.open(profile);

            SipAudioCall.Listener listener = new SipAudioCall.Listener() {
                @Override
                public void onCallEstablished(SipAudioCall call) {
                    call.startAudio();
                    call.setSpeakerMode(true);
                }

                @Override
                public void onCallEnded(SipAudioCall call) {
                    // Do something after call ends...
                }
            };

            call = manager.makeAudioCall(profile.getUriString(), "sipAddressToCall", listener, 30);

        } catch (Exception e) {
            if (profile != null) {
                try {
                    manager.close(profile.getUriString());
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }

            if (call != null) {
                call.close();
            }
        }
    }
}