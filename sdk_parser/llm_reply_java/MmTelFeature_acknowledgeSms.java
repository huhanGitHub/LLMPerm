public class MmTelFeature_acknowledgeSms {
    public void test_MmTelFeature_acknowledgeSms() {
        try {
            // Get the MmTelFeature instance
            int subId = SubscriptionManager.getDefaultSubscriptionId();
            IImsMmTelFeature binder = ImsManager.getInstance(this, subId).getImsServiceFeatureCallback();
            MmTelFeature mMmTelFeature = new MmTelFeature(this, binder);

            // Parameters required by acknowledgeSms method
            int token = 0; // This is usually obtained when the SMS is received
            int messageRef = 0; // This is the SMS message reference
            boolean success = true; // Whether the SMS was successfully received

            // Acknowledge the receipt of the SMS
            try {
                mMmTelFeature.acknowledgeSms(token, messageRef, AndroidTelephonySmsIntents.RESULT_SMS_HANDLED);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            // Verifying if the receipt was acknowledged successfully
            int status = mMmTelFeature.getSmsAcknowledgeStatus();
            if (status == AndroidTelephonySmsIntents.RESULT_SMS_HANDLED) {
                Toast.makeText(this, "SMS Acknowledged Successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "SMS Acknowledge Failed, Status: " + status, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}