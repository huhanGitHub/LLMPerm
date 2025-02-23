import android.content.Context;
import android.util.Log;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;

public class AdId_getAdId {

    private static final String TAG = "AdId_getAdId";

    public void test_AdId_getAdId(Context context) throws Exception {
        Info adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
        String adId = adInfo.getId();
        Log.i(TAG, "Ad ID: " + adId);

        boolean isLAT = adInfo.isLimitAdTrackingEnabled();
        if (isLAT) {
            Log.i(TAG, "Limit Ad Tracking is enabled");
        } else {
            Log.i(TAG, "Limit Ad Tracking is disabled");
        }
    }
}