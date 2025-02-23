public class InstallSourceInfo_getOriginatingPackageName {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void test_InstallSourceInfo_getOriginatingPackageName(){
        PackageManager packageManager = getPackageManager();     //Get the PackageManager reference
        String packageName = this.getPackageName();              //Get the current app's package name

        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_META_DATA);
            String originatingPackage = packageInfo.installSourceInfo.getOriginatingPackageName();

            if (originatingPackage != null) {
                Log.d("SourceInfo", "The app was installed by: " + originatingPackage);
            } else {
                Log.d("SourceInfo", "The app was installed from an unknown source.");
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}