public class MmTelFeature_setTerminalBasedCallWaitingStatus {
    public void test_MmTelFeature_setTerminalBasedCallWaitingStatus() {
        try {
            //Create a Context object
            Context context = this;

            // Use TelephonyManager to get IMS service
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            if (telephonyManager == null) {
                Log.e("MmTelFeature Test", "Could not get the TelephonyManager.");
                return;
            }

            // Create MmTelFeature object
            MmTelFeature mmTelFeature = telephonyManager.getImsMmTelFeature();

            if (mmTelFeature == null) {
                Log.e("MmTelFeature Test", "Could not get the MmTelFeature.");
                return;
            }

            //Set terminal based call waiting status. The first parameter is the status (true/false), and the second is a pending intent that will be triggered when the operation is completed.
            mmTelFeature.setTerminalBasedCallWaitingStatus(true, PendingIntent.getBroadcast(
                    this, 
                    0, 
                    new Intent("com.example.test.MmTelFeature_setTerminalBasedCallWaitingStatus"), 
                    0));
            Log.d("MmTelFeature Test", "setTerminalBasedCallWaitingStatus executed successfully.");

         } catch (Exception e) {
             Log.e("MmTelFeature Test", "Error while setting TerminalBasedCallWaitingStatus.", e);
         }
    }
}