public class SmsManager_updateMessageOnSim {

    private static final int SMS_INDEX_ON_SIM = 1; // replace with actual index of SMS on the SIM

    public void test_SmsManager_updateMessageOnSim() {
        // Get the default instance of the SmsManager
        SmsManager smsManager = SmsManager.getDefault();

        // Data of the new SMS in PDU format
        byte[] pduData = new byte[]{}; // replace with real PDU data here

        // Update the SMS at the given index on SIM
        boolean updated = smsManager.updateMessageOnIcc(SMS_INDEX_ON_SIM, SmsManager.STATUS_ON_ICC_READ, pduData);

        if (updated) {
            Log.d("SMS Update", "SMS updated successfully");
        } else {
            Log.d("SMS Update", "SMS update failed");
        }
    }
}