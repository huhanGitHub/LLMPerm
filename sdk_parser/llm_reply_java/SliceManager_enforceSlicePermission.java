import android.app.slice.SliceManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.RequiresApi;

public class SliceManager_enforceSlicePermission {
    public void test_SliceManager_enforceSlicePermission() {
        Uri myUri = Uri.parse("content://com.example.app/myslice");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            SliceManager sliceManager = (SliceManager) getSystemService(Context.SLICE_SERVICE);

            try {
                sliceManager.enforceSlicePermission(myUri, getPackageName());
                System.out.println("Slice permission is granted.");
            } catch (SecurityException e) {
                System.out.println("Slice permission is NOT granted. " + e.getMessage());
            }
        }
    }
}