import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

public class CarrierMessagingService_downloadMms {

    private static final int MY_PERMISSIONS_REQUEST_READ_SMS = 1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public void test_CarrierMessagingService_downloadMms() {
        // Check if the READ_SMS permission is already available.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // READ_SMS permission has not been granted.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS},
                    MY_PERMISSIONS_REQUEST_READ_SMS);
        } else {
            // READ_SMS is already available, we can use CarrierMessagingService to download MMS
            try {
                // You can not initialize it like this as it's an abstract class. Mentioned for understanding purpose.
                CarrierMessagingService carrierMessagingService = new CarrierMessagingService();
                Uri mmsUri = Uri.parse("content://mms-sms/");
                carrierMessagingService.downloadMms(mmsUri, new CarrierMessagingService.Callback() {
                    @Override
                    public void onDownloadMmsComplete(int result) {
                        Log.d("DownloadMmsComplete", "Result: " + result);
                    }

                    @Override
                    public void onSendMmsComplete(int result, byte[] sendConfPdu) {
                        Log.d("SendMmsComplete", "Result: " + result);
                    }

                    @Override
                    public void onFilterComplete(int result) {
                        Log.d("FilterComplete", "Result: " + result);
                    }

                    @Override
                    public void onSendMultipartSmsComplete(int result, int[] messageRef) {
                        Log.d("SendMultipartSmsComplete", "Result: " + result);
                    }

                    @Override
                    public void onSendSmsComplete(int result, int messageRef) {
                        Log.d("SendSmsComplete", "Result: " + result);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Here is the callback method that handles the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
                    test_CarrierMessagingService_downloadMms();
                } else {
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}