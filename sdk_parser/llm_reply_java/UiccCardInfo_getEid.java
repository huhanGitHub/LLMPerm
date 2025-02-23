public class UiccCardInfo_getEid {
    public void test_UiccCardInfo_getEid(Activity activity) {
        // Check if app has permissions to read phone state
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) 
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            return;
        }

        // Acquire a reference to the system Telephony Manager
        TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);

        // Use the telephonyManager to get the Uicc Card information.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            List<UiccCardInfo> uiccCardInfoList = telephonyManager.getUiccCardsInfo();

            // Loop through each available card
            for (UiccCardInfo uiccCardInfo : uiccCardInfoList) {
                // Retrieve the EID of each card
                String eid = uiccCardInfo.getEid();

                // Print EID to logs
                Log.i("UiccCardInfo", "Card EID: " + eid);
            }
        } else {
            Log.i("UiccCardInfo", "UiccCardInfo not supported on this device/version");
        }
    }
}