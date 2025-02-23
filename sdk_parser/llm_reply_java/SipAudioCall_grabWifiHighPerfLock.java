public class SipAudioCall_grabWifiHighPerfLock {
    public void test_SipAudioCall_grabWifiHighPerfLock() {
        SipManager sipManager = null;
        SipProfile sipProfile = null;
        SipAudioCall.Listener listener;

        try {
            sipManager = SipManager.newInstance(this);

            // Establish a SIP profile. Replace with your own SIP account details.
            SipProfile.Builder builder = new SipProfile.Builder("username", "domain.com");
            builder.setPassword("password");
            sipProfile = builder.build();

            // Create the listener for the SIP call.
            listener = new SipAudioCall.Listener() {

                @Override
                public void onCallEstablished(SipAudioCall call) {
                    call.startAudio();
                    call.setSpeakerMode(true);
                    if(call.isMuted()) {
                        call.toggleMute();
                    }
                    
                    super.onCallEstablished(call);
                }

                @Override
                public void onCallEnded(SipAudioCall call) {
                    // When the call ends, close resources.
                    if (call != null) {
                        call.close();
                    }
                }
            };

            // Make the call
            sipManager.makeAudioCall(sipProfile.getUriString(), "sip:example@sip.domain.com", listener, 30);

        } catch (ParseException pe) {
            pe.printStackTrace();
            // If domain or username information was not valid
        } catch (SipException se) {
            se.printStackTrace();
            // If any SIP API related operation failed.
        } 
    }
}