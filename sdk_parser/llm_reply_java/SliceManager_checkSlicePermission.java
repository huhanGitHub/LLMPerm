import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.app.slice.SliceManager;

public class SliceManager_checkSlicePermission {

    public void test_SliceManager_checkSlicePermission(Context context, Uri sliceUri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            SliceManager sliceManager = context.getSystemService(SliceManager.class);

            // Check if the app has permission to access the slice.
            int permission = sliceManager.checkSlicePermission(sliceUri, android.os.Process.myPid(), android.os.Process.myUid());

            if (permission == PackageManager.PERMISSION_GRANTED) {
                Log.d("SlicePermission", "App has permission to access the slice.");
            } else {
                Log.d("SlicePermission", "App does not have permission to access the slice.");
            }
        }
    }
}