public class Fragment_shouldShowRequestPermissionRationale {

    @Test
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void test_Fragment_shouldShowRequestPermissionRationale() {
        // create fragment manager
        FragmentManager fragmentManager = ApplicationProvider.getApplicationContext().getFragmentManager();

        // create mock fragment
        Fragment mockFragment = mock(Fragment.class);

        // return your_fragment when getCurrentFragment is called
        when(fragmentManager.findFragmentByTag("your_fragment")).thenReturn(mockFragment);

        // Fake it as if the fragment has never requested permission before
        when(mockFragment.shouldShowRequestPermissionRationale("your_permission")).thenReturn(false);

        Fragment currentFragment = fragmentManager.findFragmentByTag("your_fragment");

        boolean showRationale = currentFragment.shouldShowRequestPermissionRationale("your_permission");

        assertFalse("shouldShowRequestPermissionRationale returned true!", showRationale);
    }
}