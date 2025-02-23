public class SmsMessage_getOriginatingAddress {
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void test_SmsMessage_getOriginatingAddress(SmsMessage smsMessage) {
        // Get the originating address (phone number) of this SMS message.
        String originatingAddress = smsMessage.getOriginatingAddress();

        // Log the originating address for debugging purposes.
        Log.d("SMSMessage", "Originating Address: " + originatingAddress);
    }
}