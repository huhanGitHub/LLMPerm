import android.content.ContentResolver;
import android.os.Bundle;
import android.accounts.Account;

public class AbstractThreadedSyncAdapter_startSync {
    public static final String AUTHORITY = "com.example.app.sync";
    public static final String ACCOUNT_TYPE = "com.example";
    public static final Account ACCOUNT = new Account("dummyaccount", ACCOUNT_TYPE);

    public void test_AbstractThreadedSyncAdapter_startSync() {
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

        // Request the sync for the default account, authority, and manual sync settings
        ContentResolver.requestSync(ACCOUNT, AUTHORITY, settingsBundle);
    }
}