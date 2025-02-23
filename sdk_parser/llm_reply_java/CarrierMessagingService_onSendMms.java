public class CarrierMessagingService_onSendMms {
    public void test_CarrierMessagingService_onSendMms() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED || 
                ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
    
            // Permission is not granted, request for permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE},
                    1);
        } else {
            // Permission has already been granted
            sendMMS();
        }
    }
    
    private void sendMMS() {
        SmsManager smsManager = SmsManager.getDefault();
        String destinationAddress = "1234567890";  // Destination Phone number to send MMS
        String scAddress = null;  // Service Center Address, null in most cases 
        PendingIntent sentIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT"), 0);
        PendingIntent deliveryIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED"), 0);
     
        smsManager.sendMultimediaMessage(Uri.parse("file://path_to_your_mms"),
                scAddress, null, null, sentIntent);
        
        // or 
        // smsManager.sendMultipartTextMessage(destinationAddress, scAddress, parts, sentIntents, deliveryIntents);
    }
}