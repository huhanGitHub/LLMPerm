public class SmsMessage_getServiceCenterAddress {
    public void test_SmsMessage_getServiceCenterAddress() {
        byte[] pdu = { // dummy PDU data for testing
            (byte)0x07, (byte)0x91, (byte)0x16, (byte)0x92, (byte)0x53, (byte)0x85, (byte)0x16, (byte)0xF4,
            (byte)0x04, (byte)0x0C, (byte)0xD8, (byte)0x72, (byte)0x32, (byte)0x88, (byte)0x59, (byte)0x00,
            (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x0A, (byte)0x00, (byte)0x31, (byte)0x00,
            (byte)0x32, (byte)0x00, (byte)0x33, (byte)0x00, (byte)0x34, (byte)0x00, (byte)0x35, (byte)0x00
        };

        // create SmsMessage from a raw PDU
        SmsMessage sms = SmsMessage.createFromPdu(pdu);

        if (sms != null) {
            // getServiceCenterAddress method returns the SMSC address for this SMS message
            String serviceCenterAddress = sms.getServiceCenterAddress();
            
            // output the service center address for debug
            Log.d("SMSC", "Service Center Address: " + serviceCenterAddress);
        } else {
            Log.d("Error", "Cannot create SmsMessage from Pdu");
        }
    }
}