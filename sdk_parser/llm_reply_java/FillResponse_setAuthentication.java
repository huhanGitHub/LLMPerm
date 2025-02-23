public class FillResponse_setAuthentication {
    @TargetApi(Build.VERSION_CODES.O)
    public void test_FillResponse_setAuthentication() {
        IntentSender intentSender = PendingIntent.getActivity(
            this, 
            0, 
            new Intent(this, MainActivity.class),
            PendingIntent.FLAG_UPDATE_CURRENT
        ).getIntentSender();
        
        AutofillId autofillId = new AutofillId(1);

        // Create an array of AutofillId to be passed into setAuthentication() method
        AutofillId[] autofillIds = new AutofillId[]{ autofillId };

        // Create a FillResponse
        FillResponse fillResponse = new FillResponse.Builder()
            .setAuthentication(autofillIds, intentSender, new Dataset.Builder().build())
            .build();

        // Log the FillResponse for debugging
        Log.d("FillResponseTest", fillResponse.toString());
    }
}