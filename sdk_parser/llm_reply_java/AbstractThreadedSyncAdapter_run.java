import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

public class AbstractThreadedSyncAdapter_run {

    public class SyncAdapter extends AbstractThreadedSyncAdapter {

        private final Context mContext;

        public SyncAdapter(Context context, boolean autoInitialize) {
            super(context, autoInitialize);
            mContext = context;
        }

        @Override
        public void onPerformSync(Account account, Bundle extras, String authority,
                                  ContentProviderClient provider, SyncResult syncResult) {
            // your sync logic here
        }
    }

    public class MainActivity extends AppCompatActivity {

        private SyncAdapter mSyncAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            mSyncAdapter = new SyncAdapter(this, true);
            test_abstractThreadedSyncAdapter_run();
        }

        private void test_abstractThreadedSyncAdapter_run() {
            Account dummyAccount= new Account("dummyaccount", "com.example");
            Bundle bundle = null;      // can be used to pass additional options
            mSyncAdapter.onPerformSync(dummyAccount, bundle, "com.example", null, null);
        }
    }
}