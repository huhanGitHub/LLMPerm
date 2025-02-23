public class CarrierMessagingService_onSendMultipartTextSms {
    public void test_CarrierMessagingService_onSendMultipartTextSms(){
        CarrierMessagingService.RequestRateAdapter adapter = new CarrierMessagingService.RequestRateAdapter();
        ArrayList<String> messageParts = new ArrayList<>();
        messageParts.add("Hello");
        messageParts.add("world!");

        CarrierMessagingService carrierMessagingService = new CarrierMessagingService();
        carrierMessagingService.sendMultipartTextSms(messageParts,0, null ,adapter);
    }
}