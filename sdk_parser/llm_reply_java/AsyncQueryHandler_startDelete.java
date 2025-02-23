public class AsyncQueryHandler_startDelete {
    import android.content.AsyncQueryHandler;
    import android.content.ContentResolver;
    import android.net.Uri;
    import android.provider.ContactsContract;

    private static final String TAG = "AsyncQueryHandler_startDelete";
    private static final int DELETE_TOKEN = 1001;

    private void test_AsyncQueryHandler_startDelete(MyAsyncQueryHandler myAsyncQueryHandler) {
        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, "1");
        String selection = null;
        String[] selectionArgs = null;

        myAsyncQueryHandler.startDelete(DELETE_TOKEN, null, uri, selection, selectionArgs);
    }

    private static class MyAsyncQueryHandler extends AsyncQueryHandler {
        public MyAsyncQueryHandler(ContentResolver cr) {
            super(cr);
        }

        @Override
        protected void onDeleteComplete(int token, Object cookie, int result) {
            if (token == DELETE_TOKEN) {
                Log.d(TAG, "Delete operation completed. " + result + " rows deleted.");
            }
        }
    }
}