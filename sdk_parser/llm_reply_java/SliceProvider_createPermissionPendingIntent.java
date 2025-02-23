public class SliceProvider_createPermissionPendingIntent {

    public void test_SliceProvider_createPermissionPendingIntent() {
        SliceProvider sliceProvider = new SliceProvider() {
            @Override
            public boolean onCreateSliceProvider() {
                return false;
            }

            @Nullable
            @Override
            public Slice onBindSlice(Uri sliceUri) {
                return null;
            }

            @Override
            public boolean checkAccessAllowed(Uri sliceUri) {
                return false;
            }
        };

        String pkg = "com.example.yourapp";
        Uri uri = Uri.parse("content://" + pkg + "/path");
        PendingIntent pendingIntent = sliceProvider.createPermissionRequest(uri, pkg);

        if (pendingIntent != null) {
            try {
                pendingIntent.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "PendingIntent was null");
        }
    }
}