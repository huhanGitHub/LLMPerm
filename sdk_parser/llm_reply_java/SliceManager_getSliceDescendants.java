public class SliceManager_getSliceDescendants {
    
    @RequiresApi(api = Build.VERSION_CODES.P)
    public void test_SliceManager_getSliceDescendants() {
        Uri sliceUri = Uri.parse("content://com.example.android.app/slice");
        SliceManager sliceManager = getSystemService(SliceManager.class);
        Set<Uri> sliceDescendants = sliceManager.getSliceDescendants(sliceUri);

        for (Uri uri : sliceDescendants) {
            System.out.println("Slice Descendant URI: " + uri);
            try {
                Slice slice = sliceManager.bindSlice(uri);
                System.out.println("Slice: " + slice);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}