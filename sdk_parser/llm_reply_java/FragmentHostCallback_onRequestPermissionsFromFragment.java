public class FragmentHostCallback_onRequestPermissionsFromFragment {
    public void test_FragmentHostCallback_onRequestPermissionsFromFragment() {
        //Test the method here.
        String[] permissions = {Manifest.permission.CAMERA};
        fragmentHostCallback.onRequestPermissionsFromFragment(null, permissions, 123);
    }
}