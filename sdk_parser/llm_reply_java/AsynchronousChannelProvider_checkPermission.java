public class AsynchronousChannelProvider_checkPermission {

    private void test_AsynchronousChannelProvider_checkPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!isNetworkPermissionGranted()) {
                requestNetworkPermission();
            } else {
                // If permission is already granted
                // Put here the code to execute when permission is granted
            }
        } else {
            // If the SDK is lower than Marshmallow
            // Put here the code to execute when permission is granted
        }
    }

    private boolean isNetworkPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestNetworkPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_NETWORK_STATE)) {
            // Show an explanatory UI to the user
            // After explanation, ask for the permission again
           ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE},
                    REQUEST_PERMISSION_CODE);
        } else {
            // If the user has not been shown an explanation UI, ask for the permission
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_NETWORK_STATE},
                    REQUEST_PERMISSION_CODE);
        }
    }

    // Handling permission request result
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                // Put here the code to execute when permission is granted
            } else {
                // Permission denied 
                // Put here the code to handle when permission is denied 
            }
        }
    }
}