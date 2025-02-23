public class SmsManager_sendDataMessage {
    private void test_SmsManager_sendDataMessage(){
        try {
            String smsBody="Hello, this is a test SMS!";
            String destinationAddress= "1234567890";

            SmsManager smsManager = SmsManager.getDefault();
            short smsPort = 8901;
            byte[] smsBodyBytes = smsBody.getBytes();

            PendingIntent sentIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT"), 0);
            PendingIntent deliveryIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED"), 0);

            smsManager.sendDataMessage(destinationAddress, null, smsPort, smsBodyBytes, sentIntent, deliveryIntent);

        } catch (Exception ex) {
            Log.d("Exception ",ex.getMessage());
        }
    }
}