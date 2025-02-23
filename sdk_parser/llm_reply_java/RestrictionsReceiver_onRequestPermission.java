public class RestrictionsReceiver_onRequestPermission {
    public void test_RestrictionsReceiver_onRequestPermission() {
        if(ContextCompat.checkSelfPermission(this, 
               Manifest.permission.RESTRICTIONS_RECEIVER)) {
            
            // Permission is already granted, we can continue
            useRestrictionReceiver();
            
        } else {

            // Permission is not granted. Let's ask for it.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RESTRICTIONS_RECEIVER},
                    MY_PERMISSIONS_REQUEST_RESTRICTIONS_RECEIVER);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
        @NonNull String permissions[], @NonNull int[] grantResults) {
            
        if (requestCode == MY_PERMISSIONS_REQUEST_RESTRICTIONS_RECEIVER) {
            
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                
                // permission was granted, so we can now use the RestrictionsReceiver
                useRestrictionReceiver();
                
            } else {
                
                // permission denied, boo! Refuse to use RestrictionsReceiver or handle it.
                Toast.makeText(this, "Danied", Toast.LENGTH_SHORT).show();
            }
            
        }
        
    }


    private void useRestrictionReceiver(){
        RestrictionsReceiver restrictionsReceiver = new RestrictionsReceiver() {

            @Override
            public void onRequestPermission(Context context, Intent intent, int userId) {
                // Here is your override method for onRequestPermission
            }
            
        };

        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_GET_RESTRICTION_ENTRIES);
            registerReceiver(restrictionsReceiver, filter);
        } catch(Exception e) {
            Log.e("test_Restrictions", "Failed to register receiver", e);
        }
    }
}