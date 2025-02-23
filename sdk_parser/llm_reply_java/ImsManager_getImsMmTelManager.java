import android.content.Context;
import android.telephony.ims.ImsManager;
import android.telephony.ims.ImsMmTelManager;
import android.telephony.ims.feature.MmTelFeature;
import android.util.Log;

public class ImsManager_getImsMmTelManager {

    private static final String TAG = "ImsManager_getImsMmTelManager";

    public void test_ImsManager_getImsMmTelManager(Context context) {
        try {
            ImsManager imsManager = context.getSystemService(ImsManager.class);
            if (imsManager != null) {
                if (imsManager.isMmTelFeatureSupported()) {
                    ImsMmTelManager imsMmTelManager = imsManager.getImsMmTelManager();
                    Log.i(TAG, "Successfully got the ImsMmTelManager.");
                } else {
                    Log.i(TAG, "This device does not support MmTel Feature.");
                }
            } else {
                Log.e(TAG, "Could not retrieve ImsManager, it was null.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error while trying to get ImsMmTelManager: ", e);
        }
    }
}