public class SmsMessage_getSubmitPdu {
    public void test_SmsMessage_getSubmitPdu() {
        // Destination phone number
        String destinationAddress = "+1234567890";
        // SMS message body
        String message = "This is a test message";

        // Code to check if SMS service is available
        PackageManager pm = getPackageManager();
        boolean isSmsCapabilitySupported = pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY);

        if (isSmsCapabilitySupported) {
            // Check permissions
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
            } else {
                // Create submitPdu
                SmsMessage.SubmitPdu pdu = SmsMessage.getSubmitPdu("00", destinationAddress, message, false);

                //Note that SMS sending functionality is blocked for safety reason
                //Use below code on your responsibility as it might incur SMS charges

                // Send the SMS
                //SmsManager smsManager = SmsManager.getDefault();
                //smsManager.sendRawPdu(pdu.encodedScAddress, pdu.encodedMessage);
        
                // printing for test
                if (pdu != null) {
                    System.out.println("SC Address: " + new String(pdu.encodedScAddress));
                    System.out.println("Message: " + new String(pdu.encodedMessage));
                } else {
                    System.out.println("Unable to create SubmitPdu");
                }
            }
        } else {
            System.out.println("This device does not support SMS service.");
        }
    }
}