public class CarrierMessagingService_onSendDataSms {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;

    public void test_CarrierMessagingService_onSendDataSms(Context context) {

        // Check Permission
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) 
                                          != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
                Toast.makeText(this, "Permission required to send message.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        } else {
            sendSMS(context);
        }
        
    }

    private void sendSMS(Context context) {
        SmsManager smsManager = SmsManager.getDefault();
        String destinationAddress = "1234567890";
        byte[] data = "Send SMS using CarrierMessagingService".getBytes();
        short destinationPort = (short) 12345;
        
        // Send Data Message
        smsManager.sendDataMessage(destinationAddress, null, destinationPort, data, 
                                   null, null);

        Toast.makeText(context, "Message sent.", Toast.LENGTH_LONG).show();
    }

    // Request Permissions Result
    public void onRequestPermissionsResult(int requestCode, String permissions[], 
                                           int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSMS(this); 
                } else {
                    Toast.makeText(this,
                        "SMS send permission denied.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }
}