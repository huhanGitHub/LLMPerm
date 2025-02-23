public class SipManager_unregister {

    public void test_SipManager_unregister() {
        if (mSipManager == null) {
            mSipManager = SipManager.newInstance(this);
        }
        try {
            if (mSipProfile != null) {
                mSipManager.unregister(mSipProfile, this);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to unregister SIP account", Toast.LENGTH_SHORT).show();
        }
    }
}