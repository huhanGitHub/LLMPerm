public class MmTelFeature_sendSms {
    private void test_MmTelFeature_sendSms() {
        final String phoneNumber = "1234567890";
        final String message = "Hello world!";

        // This is just for the test, you should get this service in a proper lifecycle method.
        MmTelFeature mmTelFeature = new MmTelFeature();

        try {
            mmTelFeature.sendSms(1, 1, phoneNumber, "", 1, message, PendingIntent.getBroadcast(
                    this, 0, new Intent("SMS_SENT"), 0), PendingIntent.getBroadcast(
                    this, 0, new Intent("SMS_DELIVERED"), 0)
            );

            Toast.makeText(this, "SMS sent.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Failed to send SMS.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}