public class SipAudioCall_setSpeakerMode {
    public void test_SipAudioCall_setSpeakerMode() {
        try {
            SipManager sipManager = SipManager.newInstance(this);
            SipProfile.Builder builder = new SipProfile.Builder("userName", "serverDomainName");
            builder.setPort(5060);
            builder.setProtocol("UDP");
            builder.setPassword("password");

            SipProfile sipProfile = builder.build();

            SipAudioCall.Listener listener = new SipAudioCall.Listener() {
                @Override
                public void onCallEstablished(SipAudioCall call) {
                    call.startAudio();
                    call.setSpeakerMode(true);
                    System.out.println("Call established. Speaker mode is set to: " + call.getSpeakerMode());
                }

                @Override
                public void onError(SipAudioCall call, int errorCode, String errorMessage) {
                    System.err.println("Error occurred in SIP audio call with error code: " + errorCode + ". Error message: " + errorMessage);
                }
            };

            sipManager.open(sipProfile);
            SipAudioCall sipAudioCall = sipManager.makeAudioCall(sipProfile.getUriString(), "receiverUserName@serverDomainName", listener, 30);
            sipAudioCall.setSpeakerMode(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}