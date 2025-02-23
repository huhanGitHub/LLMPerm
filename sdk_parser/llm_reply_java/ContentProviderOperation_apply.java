import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.provider.ContactsContract;
import android.net.Uri;
import android.os.RemoteException;
import java.util.ArrayList;

public class ContentProviderOperation_apply {
    public void test_ContentProviderOperation_apply() {
        ArrayList<ContentProviderOperation> operations = new ArrayList<>();

        // The base URI for the Contacts Provider
        Uri uri = ContactsContract.RawContacts.CONTENT_URI;

        // Prepare the operation for inserting a new contact
        ContentProviderOperation operation1 = ContentProviderOperation.newInsert(uri)
            .withValue(ContactsContract.RawContacts.DISPLAY_NAME, "John Doe")
            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, "1234567890")
            .build();
        operations.add(operation1);

        // Prepare the operation for updating the phone number of the contact
        ContentProviderOperation operation2 = ContentProviderOperation.newUpdate(uri)
            .withSelection(ContactsContract.RawContacts.DISPLAY_NAME + "=?", new String[]{"John Doe"})
            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, "0987654321")
            .build();
        operations.add(operation2);

        // Apply the batch of operations
        ContentResolver contentResolver = getContentResolver();
        try {
            ContentProviderResult[] results = contentResolver.applyBatch(ContactsContract.AUTHORITY, operations);
        } catch (RemoteException | OperationApplicationException e) {
            e.printStackTrace();
        }
    }
}