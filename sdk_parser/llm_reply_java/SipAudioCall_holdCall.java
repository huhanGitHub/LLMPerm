public class SipAudioCall_holdCall {
    private SipAudioCall sipAudioCall;

    public void test_SipAudioCall_holdCall() throws SipException {
        if (sipAudioCall != null && !sipAudioCall.isOnHold()) {
            sipAudioCall.holdCall(30);
        }
    }
}