public class VisualVoicemailService_onSmsReceived {
    public void test_VisualVoicemailService_onSmsReceived() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.NEW_VVM");
        String dialString = "number_to_dial";
        intent.putExtra(TelephonyManager.EXTRA_PHONE_ACCOUNT_HANDLE, dialString);
        VisualVoicemailService visualVoicemailService = new VisualVoicemailService() {
            @Override
            public void onSmsReceived(VisualVoicemailTask task) {
                // Place test logic here. 
            }
        };

        // Simulate the system call for a new VVM. 
        visualVoicemailService.onHandleIntent(intent);
    }
}