public class SliceProvider_handleBindSlice {
    public void test_SliceProvider_handleBindSlice() {
        ExampleSliceProvider sliceProvider = new ExampleSliceProvider();

        // Create an intent
        Intent testIntent = new Intent(this, ExampleActivity.class);

        // onHandleIntent will call onMapIntentToUri() and onBindSlice()
        sliceProvider.onHandleIntent(testIntent);
    }
}