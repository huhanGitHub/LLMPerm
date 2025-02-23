public class UiccCardInfo_getIccId {
    public String test_UiccCardInfo_getIccId(Context context) {
        if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // The app doesn't have permission to read phone state
            return null;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            List<UiccCardInfo> uiccCardInfos = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                uiccCardInfos = telephonyManager.getUiccCardsInfo();
            }
            if (uiccCardInfos != null && !uiccCardInfos.isEmpty()) {
                return uiccCardInfos.get(0).getIccId();
            }
        }
        return null;
    }
}