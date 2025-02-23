public class QuickAccessWalletServiceInfo_tryCreate {

    public void test_QuickAccessWalletServiceInfo_tryCreate() throws PackageManager.NameNotFoundException {
        PackageManager packageManager = getPackageManager();
        
        // Define the specific service you're interested in
        String service = "com.example.MyWalletService";  // Replace with actual service name
        
        // Get info for that service
        ServiceInfo si = packageManager.getServiceInfo(new ComponentName(getPackageName(), service), PackageManager.GET_SERVICES);
        
        // Create a QuickAccessWalletServiceInfo using ServiceInfo
        QuickAccessWalletServiceInfo quickAccessWalletServiceInfo = QuickAccessWalletServiceInfo.tryCreate(si);
        
        if (quickAccessWalletServiceInfo != null) {
            // Now you can get information related to the service
            String label = quickAccessWalletServiceInfo.loadLabel(packageManager).toString(); 
            Log.d("WalletServiceInfo", "Label: "+label);
            // Similarly, you can get other information like Settings Activity Name, etc.
        } else {
            Log.d("WalletServiceInfo", "Unable to retrieve QuickAccessWalletServiceInfo.");
        }
    }
}