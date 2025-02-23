public class SliceProvider_onCreatePermissionRequest extends SliceProvider {
    public void test_SliceProvider_onCreatePermissionRequest() {
        Uri uri = Uri.parse("content://com.myapp.myslice/some_path");
        try {
            String callingPackage = getCallingPackage();
            Intent intent = SliceProvider.createPermissionRequest(getContext(), uri, callingPackage);
            startActivity(intent, null);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateSliceProvider() {
        return true;
    }
}