public class Fragment_requestPermissions {

    public void test_Fragment_requestPermissions() {
        Fragment fragment = new Fragment() {
            @Override
            public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

                // Check if the only required permission has been granted
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Permission granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Permission not granted.", Toast.LENGTH_SHORT).show();
                }
            }
        };

        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED) {
            
            // Should show an explanation for the user
            if (fragment.shouldShowRequestPermissionRationale(permission)) {
                // Show an explanation to the user asynchronously
                Toast.makeText(this, "We need your permission to read external storage.", Toast.LENGTH_LONG).show();
            }
            
            fragment.requestPermissions(new String[] {permission}, 0);
        } else {
            Toast.makeText(this, "Permission already granted.", Toast.LENGTH_SHORT).show();
        }
    }
}