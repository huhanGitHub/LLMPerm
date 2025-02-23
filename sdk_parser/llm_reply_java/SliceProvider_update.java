public class SliceProvider_update {
    public void test_SliceProvider_update() {
        Uri uri = Uri.parse("content://com.example.app");
        Context context = getContext();
        if (context != null) {
            // Notify system that a certain uri has been changed
            context.getContentResolver().notifyChange(uri, null);
        }
    }
}