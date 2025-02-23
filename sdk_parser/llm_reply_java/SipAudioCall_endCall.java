public class SipAudioCall_endCall {
    public void test_SipAudioCall_endCall() {
        try {
            // Initialize manager
            SipManager manager = SipManager.newInstance(this);
            
            // Initialize SipProfile
            SipProfile.Builder builder = new SipProfile.Builder("username", "domain");
            builder.setPassword("password");
            SipProfile profile = builder.build();
            
            // Initialize SipAudioCall.Listener
            SipAudioCall.Listener listener = new SipAudioCall.Listener() {
                
                @Override
                public void onCallEstablished(SipAudioCall call) {
                    call.startAudio();
                    call.setSpeakerMode(true);
                    if (call.isMuted()) {
                        call.toggleMute();
                    }
                }
                
                @Override
                public void onCallEnded(SipAudioCall call) {
                    // Call ended, handle actions here
                }
            };
            
            // Make an outgoing call
            SipAudioCall call = manager.makeAudioCall(profile.getUriString(), "sip:destination@domain.com", listener, 30);
            
            // Now, let's end the call
            if (call != null) {
                call.endCall();
            }
            
        } catch (Exception e) {
            // Handle the exception here
        }
    }
}