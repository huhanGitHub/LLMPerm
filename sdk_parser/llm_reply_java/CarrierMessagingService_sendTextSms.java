public class CarrierMessagingService_sendTextSms {
    private void test_CarrierMessagingService_sendTextSms(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            try{
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("PHONE_NUMBER_HERE", null, "TEST MESSAGE", null, null);
                Toast.makeText(getApplicationContext(), "SMS Sent.", Toast.LENGTH_LONG).show();
            } catch (Exception e){
                Toast.makeText(getApplicationContext(), "SMS sending failed...", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        } else{
            Toast.makeText(this, "SMS Permission is not granted", Toast.LENGTH_SHORT).show();
        }
    }
}