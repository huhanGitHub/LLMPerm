public class CallingAppInfo_getOrigin {
    public void test_CallingAppInfo_getOrigin() {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.GET_ACCOUNTS}, PERMISSION_REQUEST_CODE);
        } else {
            performGetOriginTest();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    performGetOriginTest();
                } else {
                    Toast.makeText(this, "Permission denied. Cannot perform test", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
 
    private void performGetOriginTest() {
        int originUid = 12345; // Assuming this as uid
        CallingAppInfo callingAppInfo = new CallingAppInfo(originUid);
        int uid = callingAppInfo.getOrigin();
        Toast.makeText(this, "The origin UID is: " + uid, Toast.LENGTH_LONG).show();
    }
}