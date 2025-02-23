import android.content.Context;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import java.util.List;

public class NeighboringCellInfo_getNetworkType {

    public void test_NeighboringCellInfo_getNetworkType() {
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        List<NeighboringCellInfo> neighboringCellInfoList = telephonyManager.getNeighboringCellInfo();

        for (NeighboringCellInfo neighboringCellInfo : neighboringCellInfoList) {
            int networkType = neighboringCellInfo.getNetworkType();

            String networkTypeString;

            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    networkTypeString = "GPRS";
                    break;
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    networkTypeString = "EDGE";
                    break;
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    networkTypeString = "CDMA";
                    break;
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    networkTypeString = "UMTS";
                    break;
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    networkTypeString = "HSDPA";
                    break;
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    networkTypeString = "HSUPA";
                    break;
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    networkTypeString = "HSPA";
                    break;
                default:
                    networkTypeString = "UNKNOWN";
                    break;
            }
            
            System.out.println("Neighboring Cell Network Type: " + networkTypeString);
        }
    }
}