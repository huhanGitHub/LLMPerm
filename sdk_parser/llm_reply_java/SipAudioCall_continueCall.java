public class SipAudioCall_continueCall {
    void test_SipAudioCall_continueCall() {
        try {
            SipManager sipManager = SipManager.newInstance(this);
            SipProfile sipProfile = new SipProfile.Builder("username", "domain.com").setPassword("password").build();

            SipAudioCall.Listener listener = new SipAudioCall.Listener() {
                @Override
                public void onCallEstablished(SipAudioCall call) {
                    super.onCallEstablished(call);
                    call.startAudio(); // Start audio for the call
                    call.setSpeakerMode(true); // Enable Speaker mode
                    call.toggleMute(); // Mute
                }

                @Override
                public void onCallEnded(SipAudioCall call) {
                    super.onCallEnded(call);
                    // handle when the call ends.
                }
            };

            // Assuming "peer_sip_profile_uri" refers to the SIP profile URI of the individual you are calling.
            String peer_sip_profile_uri = "sip:username@domain.com";
            SipAudioCall sipAudioCall = sipManager.makeAudioCall(sipProfile.getUriString(), peer_sip_profile_uri, listener, 30);

            // Simulating pause and resume
            sipAudioCall.holdCall(30); // Hold the call for 30 seconds
            sipAudioCall.continueCall(30); // Continue the call after 30 seconds
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}