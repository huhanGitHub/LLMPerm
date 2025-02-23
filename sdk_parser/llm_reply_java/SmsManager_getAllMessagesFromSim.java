import android.app.Activity;
import android.telephony.SmsMessage;
import android.telephony.SmsManager;
import java.util.ArrayList;

public class SmsManager_getAllMessagesFromSim extends Activity {
    public void test_SmsManager_getAllMessagesFromSim() {
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> smsMessages = smsManager.getAllMessagesFromIcc();
        
        if (smsMessages != null) {
            for (String message : smsMessages) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) message);
                String sender = smsMessage.getDisplayOriginatingAddress();
                String body = smsMessage.getMessageBody().toString();

                System.out.println("SMS From: " + sender);
                System.out.println("SMS Body: " + body);
            }
        } else {
            System.out.println("No SMS messages found on SIM card.");
        }
    }
}