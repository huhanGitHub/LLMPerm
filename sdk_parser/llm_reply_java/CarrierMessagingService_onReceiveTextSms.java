public class CarrierMessagingService_onReceiveTextSms {

    @Test
    public void test_CarrierMessagingService_onReceiveTextSms() {
        // Create a mock SMS message
        SmsMessage smsMessage = mock(SmsMessage.class);
        ReceiveOptions receiveOptions = new ReceiveOptions(TelephonyManager.PHONE_TYPE_NONE);
        ResultCallback<Bundle> callback = new ResultCallback<Bundle>() {
            @Override
            public void onReceiveResult(Bundle result) {
                // Test what you want to happen when the result is received
            }
        };

        // Call the method to test
        mCarrierMessagingService.onReceiveTextSms(smsMessage, new Bundle(), receiveOptions, callback);

        // Verify that the method was called with the correct parameters
        verify(mCarrierMessagingService).onReceiveTextSms(smsMessage, new Bundle(), receiveOptions, callback);
    }
}