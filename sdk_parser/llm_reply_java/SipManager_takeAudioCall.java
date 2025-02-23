import android.net.sip.SipAudioCall;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.util.Log;

public class SipManager_takeAudioCall {

    public void test_SipManager_takeAudioCall() {
        // Instantiate SIP Manager
        SipManager manager = SipManager.newInstance(this.getActivity());

        try {
            // Create a listener for incoming calls
            SipAudioCall.Listener listener = new SipAudioCall.Listener() {

                @Override
                public void onRinging(SipAudioCall call, SipProfile caller) {
                    try {
                        // When receiving a call, answer it.
                        call.answerCall(30);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            // Take an incoming SIP audio call. The session in the incoming SIP INVITE request
            // is defined in the session description protocol (SDP) in the request and is parsed
            // into a session that is retrievable by {@link SipAudioCall#getSession}.
            SipProfile profile = SipProfile.Builder.buildFrom("user@domain.com");
            manager.takeAudioCall(profile.getUriString(), listener);

        } catch (Exception e) {
            Log.e("SIP_TEST", "Error taking audio call", e);
        }
    }
}