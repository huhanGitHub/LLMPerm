public class CellIdentityLte_getMobileNetworkOperator {
    private void test_CellIdentityLte_getMobileNetworkOperator() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            List<CellInfo> cellInfos = telephonyManager.getAllCellInfo();
            for (CellInfo cellInfo : cellInfos) {
                if (cellInfo instanceof CellInfoLte) {
                    CellInfoLte cellInfoLte = (CellInfoLte) cellInfo;
                    String mobileNetworkOperator = cellInfoLte.getCellIdentity().getMobileNetworkOperator();
                    // Do something with mobileNetworkOperator
                }
            }
        }
    }
}