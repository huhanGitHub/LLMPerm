import android.accounts.Account;
import android.content.ContentResolver;
import android.content.SyncInfo;
import android.os.Bundle;
import android.util.Log;

public class SyncInfo_createAccountRedacted {

    public void test_SyncInfo_createAccountRedacted() {
        // Create a new account and assign it to a sync adapter
        final String AUTHORITY = "example.com";
        final String ACCOUNT_TYPE = "com.example";
        final String ACCOUNT_NAME = "Test Account";
        Account mAccount = new Account(ACCOUNT_NAME, ACCOUNT_TYPE);

        // Get instance of ContentResolver
        ContentResolver contentResolver = getContentResolver();

        // Request sync for the new account
        contentResolver.requestSync(mAccount, AUTHORITY, Bundle.EMPTY);

        // Get the information from the active sync
        SyncInfo syncInfo = ContentResolver.getCurrentSync();

        // Check if the active sync is for the created account
        if (syncInfo != null && syncInfo.account.equals(mAccount)) {
            // Redact and output the account information
            String redactedAccountName = mAccount.name.replaceAll(".", "*");
            String redactedAccountType = mAccount.type.replaceAll(".", "*");
            Log.d("TEST_SYNCINFO", "SyncAccount Name: " + redactedAccountName + ", Type: " + redactedAccountType);
        }
    }
}