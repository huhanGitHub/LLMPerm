import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;

public class CarrierMessagingService_sendDataSms {

    private static final int SEND_SMS_PERMISSION_REQUEST_CODE = 101;

    public void test_CarrierMessagingService_sendDataSms() {
        // Check for SEND_SMS permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        } else {
            sendSms();
        }
    }

    private void sendSms() {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            String destinationAddress = "1234567890";  // Replace with your recipient number
            short destinationPort = 12345;  // Replace with your destination port
            byte[] data = "Hello World".getBytes();
            smsManager.sendDataMessage(destinationAddress, null, destinationPort, data, null, null);
            Log.i("SMS", "SMS Sent Successfully");
        } catch (Exception e) {
            Log.e("SMS", "Failed to send SMS", e);
        }
    }

    //This is called by the system once the user, responds to the permissions dialog
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case SEND_SMS_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSms();
                }
                return;
        }
    }
}