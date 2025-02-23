import android.content.Context;
import android.content.res.ObbInfo;
import android.content.res.ObbScanner;
import android.util.Log;
import java.io.IOException;

public class ObbScanner_getObbInfo {
    public void test_ObbScanner_getObbInfo(Context context, String filePath) {
        ObbInfo obbInfo = null;
        try {
            // Scan the OBB file
            obbInfo = ObbScanner.getObbInfo(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (obbInfo != null) {
            // Print OBB info
            Log.d("OBB_INFO", "Package Name: " + obbInfo.packageName +
                                ", Version: " + obbInfo.version +
                                ", Flags: " + obbInfo.flags);
                            
        } else {
            Log.d("OBB_INFO", "OBB info could not be retrieved.");
        }
    }
}