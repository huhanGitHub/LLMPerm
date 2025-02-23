public class SmsManager_deleteMessageFromSim {
    public void test_SmsManager_deleteMessageFromSim() {
        // Normally, you would get this from a list of all SMS messages on the SIM card
        int messageIndex = 0;
        SmsManager smsManager = SmsManager.getDefault();
        boolean isSuccess = smsManager.deleteMessageFromIcc(messageIndex);

        if (isSuccess) {
            Toast.makeText(getApplicationContext(), 
                           "Message deleted from SIM successfully", 
                           Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), 
                           "Failed to delete the message from SIM", 
                           Toast.LENGTH_LONG).show();
        }
    }
}