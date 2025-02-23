public class CarrierMessagingService_onSendTextSms {
    public void test_CarrierMessagingService_onSendTextSms() {
        CarrierMessagingService carrierMessagingService = new CarrierMessagingService() {
            @Override
            public void onSendTextSms(String text, int subId, String destAddress, ResultCallback<Integer> resultCallback) {
                super.onSendTextSms(text, subId, destAddress, resultCallback);
                // The logic to send SMS goes here
            }
        };

        String text = "Hello, World!";
        int subId = 1;
        String destAddress = "1234567890";
        carrierMessagingService.onSendTextSms(text, subId, destAddress, new ResultCallback<Integer>() {
            @Override
            public void onReceiveResult(Integer result) {
                // Handle Result here
            }
        });
    }
}