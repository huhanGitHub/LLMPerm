public class SliceProvider_delete {
    public void test_SliceProvider_delete() {
        ContentResolver resolver = getContentResolver();
        Uri uri = new Uri.Builder().appendPath("content://your.authority").appendPath("path_to_slice").build();

        int deleted;
        try {
            deleted = resolver.delete(uri, null, null);
        } catch (Exception ex) {
            Log.e("MainActivity", "Error deleting slice with URI: " + uri.toString(), ex);
            deleted = 0;
        }

        if (deleted > 0) {
            Toast.makeText(this, "Slice deleted successfully.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Slice deletion failed.", Toast.LENGTH_SHORT).show();
        }
    }
}