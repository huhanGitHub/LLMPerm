public class SipAudioCall_startAudio {
    public void test_SipAudioCall_startAudio() {
        try {
            if(mSipManager != null) {
                // For testing, let's try to call the same SIP account
                final SipAudioCall.Listener listener = new SipAudioCall.Listener() {
                    @Override
                    public void onCallEstablished(SipAudioCall call) {
                        call.startAudio();
                        call.setSpeakerMode(true);
                        call.toggleMute();
                    }
                    // We can also implement other SipAudioCall conditions such as onCallEnded, onCallBusy, etc.
                };

                mSipManager.makeAudioCall(mSipProfile.getUriString(), mSipProfile.getUriString(), listener, 30);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}