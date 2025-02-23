import android.net.Uri;
import android.os.Build;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.slice.SliceManager;

public class SliceManager_unpinSlice {
    
    @RequiresApi(api = Build.VERSION_CODES.P)
    public void test_SliceManager_unpinSlice() {
        // Initialize Uri
        Uri sliceUri = Uri.parse("content://com.example.app/slice");

        // Get the slice manager
        SliceManager sliceManager = getSystemService(SliceManager.class);

        // Check whether SliceProvider was already pinned or not
        if (sliceManager.isSlicePinned(sliceUri)) {
            // Unpin the slice
            sliceManager.unpinSlice(sliceUri);
            
            // Check if unpinning was successful
            if (!sliceManager.isSlicePinned(sliceUri)) {
                Toast.makeText(this, "Slice unpin successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to unpin", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Slice was not pinned", Toast.LENGTH_SHORT).show();
        }
    }
}