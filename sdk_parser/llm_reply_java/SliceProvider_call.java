public class SliceProvider_call {
    public void test_SliceProvider_call() throws Exception {
        ContentResolver resolver = getContentResolver();
        Uri sliceUri = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_CONTENT)
                .authority(YOUR_SLICE_PROVIDER_AUTHORITY)
                .appendPath(YOUR_SLICE_PATH)
                .build();

        Slice slice = resolver.acquireSlice(sliceUri);
        // Now you can use your 'slice' object to interact with sliceable content
        // However, remember that actual implementation of Slice is done in SliceProvider
    }
}