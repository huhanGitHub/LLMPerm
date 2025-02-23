public class SipAudioCall_makeCall {
    private SipAudioCall.Listener listener;

    public void test_SipAudioCall_makeCall() {
        if (SipManager.isVoipSupported(this)) {
            try {
                SipManager sipManager = SipManager.newInstance(this);
                SipProfile sipProfile = new SipProfile.Builder("username", "domain.com").setPort(5060).setPassword("password").build();
                sipManager.open(sipProfile);

                listener = new SipAudioCall.Listener() {
                    @Override
                    public void onCallEstablished(SipAudioCall call) {
                        call.startAudio();
                        call.setSpeakerMode(true);
                        if (call.isMuted()) {
                            call.toggleMute();
                        }
                        super.onCallEstablished(call);
                    }

                    @Override
                    public void onCallEnded(SipAudioCall call) {
                        // Do something when call ends
                        super.onCallEnded(call);
                    }
                };

                SipAudioCall sipAudioCall = sipManager.makeAudioCall(sipProfile.getUriString(), "sip:destination@domain.com", listener, 30);

            } catch (SipException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "VOIP is not supported", Toast.LENGTH_SHORT).show();
        }
    }
}