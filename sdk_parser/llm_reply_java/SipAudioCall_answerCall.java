public class SipAudioCall_answerCall {

    public void test_SipAudioCall_answerCall() {
        final int timeout = 30;
        try {
            SipAudioCall.Listener listener = new SipAudioCall.Listener() {
                @Override
                public void onRinging(SipAudioCall call, SipProfile caller) {
                    try {
                        call.answerCall(timeout);
                    } catch (SipException e) {
                        e.printStackTrace();
                    }
                }
            };

            if (sipManager != null) {
                sipAudioCall = sipManager.takeAudioCall(intent, listener);
            }

        } catch (Exception e) {
            if (sipAudioCall != null) {
                sipAudioCall.close();
            }
        }
    }
}