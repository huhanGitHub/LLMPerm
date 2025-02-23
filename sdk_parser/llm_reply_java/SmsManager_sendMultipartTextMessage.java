public class SmsManager_sendMultipartTextMessage {
    public void test_SmsManager_sendMultipartTextMessage(String phoneNumber, String message) {

        SmsManager smsManager = SmsManager.getDefault();

        ArrayList<String> parts = smsManager.divideMessage(message);

        int numParts = parts.size();

        ArrayList<PendingIntent> sendIntents = new ArrayList<PendingIntent>();
        ArrayList<PendingIntent> deliveryIntents = new ArrayList<PendingIntent>();

        for (int i = 0; i < numParts; i++) {
            Intent msentIntent = new Intent(Intent.ACTION_SEND);
            Intent mDeliveryIntent = new Intent(Intent.ACTION_DELIVER_DATA);

            sendIntents.add(PendingIntent.getBroadcast(getApplicationContext(), 0, msentIntent, 0));
            deliveryIntents.add(PendingIntent.getBroadcast(getApplicationContext(), 0, mDeliveryIntent, 0));
        }

        smsManager.sendMultipartTextMessage(phoneNumber, null, parts, sendIntents, deliveryIntents);
 
    }
}