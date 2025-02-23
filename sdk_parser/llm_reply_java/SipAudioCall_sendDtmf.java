public class SipAudioCall_sendDtmf {
    @Test
    public void test_SipAudioCall_sendDtmf() {
        try {
            // Initialize SIP Manager
            SipManager manager = SipManager.newInstance(InstrumentationRegistry.getInstrumentation().getTargetContext());

            // Initialize SipProfile
            SipProfile.Builder builder = new SipProfile.Builder("USERNAME", "DOMAIN");
            builder.setPassword("PASSWORD");

            // Register SipProfile
            SipProfile profile = builder.build();
            manager.open(profile);

            // Make a call
            SipAudioCall call = manager.makeAudioCall(profile.getUriString(), "sip:destination@domain.com", null, 30);

            // Send DTMF
            int code = 1; //Replace with your DTMF code
            call.sendDtmf(code);

            // End call
            call.endCall();

        } catch (SipException e) {
            fail();
        } catch (IllegalArgumentException e) {
            fail();
        } catch (InterruptedException e) {
            fail();
        }
    }
}