import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;

public class IntentSender_sendIntent extends Activity {
    private static final int REQUEST_CODE = 1000;
    private static final String ACTION = "com.example.ACTION_DO_SOMETHING";

    public void test_IntentSender_sendIntent() throws IntentSender.SendIntentException {
        Intent intent = new Intent(ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, REQUEST_CODE, intent, 0);
        IntentSender intentSender = pendingIntent.getIntentSender();
        startIntentSenderForResult(intentSender, REQUEST_CODE, new Intent(), 0, 0, 0);
    }
}