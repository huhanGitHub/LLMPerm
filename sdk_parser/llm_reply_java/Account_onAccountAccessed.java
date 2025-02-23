public class Account_onAccountAccessed {
    public void test_Account_onAccountAccessed() {
        int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                //Show an explanation to the user *asynchronously*
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        } else {
            AccountManager accountManager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
            Account[] accounts = accountManager.getAccounts();

            for (Account account : accounts) {
                System.out.println("Account: " + account.name + ", " + account.type);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted
                    test_Account_onAccountAccessed();
                } else {
                    // Permission denied
                    Toast.makeText(this, "Permission denied to read your Contacts", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}