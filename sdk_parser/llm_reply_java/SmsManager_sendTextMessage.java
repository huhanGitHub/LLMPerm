public class SmsManager_sendTextMessage {
    public void test_SmsManager_sendTextMessage(String phoneNumber, String message) {
        // Get the default instance of SmsManager
        SmsManager smsManager = SmsManager.getDefault();
       
        // If message length is more than the allowed, break it into chunks
        if (message.length() > 160) {
            ArrayList<String> messageParts = smsManager.divideMessage(message);
            smsManager.sendMultipartTextMessage(phoneNumber, null, messageParts, null, null);
        } else {
            // If the message length is under 160 characters, just send it as-it-is
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        }
    }
}