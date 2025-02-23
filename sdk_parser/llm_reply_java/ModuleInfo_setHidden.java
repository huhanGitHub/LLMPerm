public class ModuleInfo_setHidden {

    private void test_ModuleInfo_setHidden() throws Exception {
        PackageManager pm = getApplicationContext().getPackageManager();
        List<ModuleInfo> moduleInfoList = pm.getInstalledModules(PackageManager.MATCH_ALL);
        for (ModuleInfo moduleInfo : moduleInfoList) {
            // if there would be this function we could use it as follows
            // moduleInfo.setHidden(true);

            // Print the module info for testing purpose
            Log.d("MainActivity", "Module Name: " + moduleInfo.getName());
            Log.d("MainActivity", "Module Package Name: " + moduleInfo.getPackageName());
            Log.d("MainActivity", "Module is Hidden: " + moduleInfo.isHidden());
        }
    }
}