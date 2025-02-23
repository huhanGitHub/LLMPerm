import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.Manifest;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class BlockedNumberContract_setEnhancedBlockSetting {
    
    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    public void test_BlockedNumberContract_setEnhancedBlockSetting(Activity activity) {
        // Check if the app has necessary permissions.
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_BLOCKED_NUMBERS) != PackageManager.PERMISSION_GRANTED  ||
            ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_BLOCKED_NUMBERS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_BLOCKED_NUMBERS, Manifest.permission.WRITE_BLOCKED_NUMBERS}, 0);
        } else {
            setEnhancedBlockSetting(activity);
        }
    }
    
    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    public void setEnhancedBlockSetting(Activity activity) {
        ContentResolver contentResolver = activity.getContentResolver();
        ContentValues contentValues = new ContentValues();
        // Set the telephone number to be blocked.
        contentValues.put(BlockedNumberContract.BlockedNumbers.COLUMN_ORIGINAL_NUMBER, "1234567890");
        // Set the enhanced blocking mode for this number.
        contentValues.put(BlockedNumberContract.BlockedNumbers.COLUMN_ENHANCED_SETTING, BlockedNumberContract.BlockedNumbers.ENHANCED_SETTING_KEY_BLOCK_PAYPHONE);
        Uri uri = contentResolver.insert(BlockedNumberContract.BlockedNumbers.CONTENT_URI, contentValues);
         if (uri != null) {
             Toast.makeText(activity, "Number blocked successfully!", Toast.LENGTH_SHORT).show();
         } else {
             Toast.makeText(activity, "Failed to block the number.", Toast.LENGTH_SHORT).show();
         }
    }
}