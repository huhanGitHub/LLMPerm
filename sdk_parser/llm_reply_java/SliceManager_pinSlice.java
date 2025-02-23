import android.app.slice.SliceManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;
import androidx.annotation.RequiresApi;

public class SliceManager_pinSlice {
    @RequiresApi(api = Build.VERSION_CODES.P)
    public void test_SliceManager_pinSlice(Context context) {
        String sliceUriString = "content://com.example.android.app/slice";
        Uri sliceUri = Uri.parse(sliceUriString);

        SliceManager sliceManager = context.getSystemService(SliceManager.class);
        boolean slicePinned = false;

        // Check if the slice has already been pinned.
        if (sliceManager.getPinnedSpecs(sliceUri).isEmpty()) {
            try {
                // Request the app to pin the slice.
                sliceManager.pinSlice(sliceUri, new Set<SliceSpec>());
                slicePinned = true;
            } catch (IllegalArgumentException | IllegalStateException e) {
                e.printStackTrace();
                Toast.makeText(context, "Failed to pin slice.", Toast.LENGTH_LONG).show();
            }
        }

        if (slicePinned) {
            Toast.makeText(context, "Successfully pinned slice.", Toast.LENGTH_LONG).show();
        }
    }
}