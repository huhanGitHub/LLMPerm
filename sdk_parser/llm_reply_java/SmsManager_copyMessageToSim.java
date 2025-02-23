public class SmsManager_copyMessageToSim {

    public void test_SmsManager_copyMessageToSim() {
        try {
            // get an instance of SmsManager
            SmsManager smsManager = SmsManager.getDefault();

            // define a dummy message and destination address
            String destinationAddress = "9876543210";
            String message = "Hello, World!";

            // Convert message into pdu
            byte[] pdu = message.getBytes();

            // copy the message to the SIM card
            boolean result = smsManager.copyMessageToSim(null, pdu, SmsManager.STATUS_ON_ICC_SENT);

            // print the result
            if (result) {
                Toast.makeText(getApplicationContext(),
                        "Message copied to SIM successfully!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Failed to copy message to SIM!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}