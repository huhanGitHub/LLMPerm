public class BlockedNumberContract_canCurrentUserBlockNumbers {
    @Test
    public void test_BlockedNumberContract_canCurrentUserBlockNumbers() {
        int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_BLOCKED_NUMBERS);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_BLOCKED_NUMBERS)) {
                Snackbar.make(this.findViewById(android.R.id.content),
                        "This app requires READ_BLOCKED_NUMBERS permission to function correctly.",
                        Snackbar.LENGTH_INDEFINITE).setAction("OK", view -> 
                        ActivityCompat.requestPermissions(Main2Activity.this,
                        new String[]{Manifest.permission.READ_BLOCKED_NUMBERS},
                        PERMISSIONS_REQUEST_READ_CONTACTS)).show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_BLOCKED_NUMBERS},
                        PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        } else {
            // Permission already granted
            // You can perform your function here that needs this permission
        }
    }
}