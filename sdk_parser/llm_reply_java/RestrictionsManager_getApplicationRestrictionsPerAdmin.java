public class RestrictionsManager_getApplicationRestrictionsPerAdmin {
    public void test_RestrictionsManager_getApplicationRestrictionsPerAdmin() {
        // get the current Activity
        Activity currentActivity = getActivity();

        // Obtain RestrictionsManager object
        RestrictionsManager restrictionsManager = (RestrictionsManager) currentActivity.getSystemService(Context.RESTRICTIONS_SERVICE);

        // Get application restrictions specific to the admin
        Bundle appRestrictions = restrictionsManager.getApplicationRestrictions();

        if (appRestrictions != null) {
            Set<String> keys = appRestrictions.keySet();
            for (String key : keys) {
                //Print each key-value pair
                //Note: The actual type of the value may need to be checked
                // and appropriate Bundle get-method may need to be called
                Log.i("RestrictionsTest", "Key: " + key + " Value: " + appRestrictions.get(key));
            }
        }
    }
}