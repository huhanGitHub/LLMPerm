import android.Manifest;
import android.content.pm.PackageManager;
import android.provider.ContactsContract;
import android.content.ContentResolver;
import android.database.Cursor;
import android.content.Context;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Contacts_tryGetMyContactsGroupId {
    
    public void test_Contacts_tryGetMyContactsGroupId(Context context){
        // Checking if READ_CONTACTS permission is already granted
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED){
            // If the permission is not already granted then asking for the permission
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            // Permission had already been granted
            getMyContactsGroupId(context);
        }
    }

    public void getMyContactsGroupId(Context context){
        String selection = ContactsContract.Groups.DELETED + "=?";
        String[] selectionArgs = { "0" };
        // defining the columns to retrieve
        String[] projection = new String[] { ContactsContract.Groups._ID, ContactsContract.Groups.TITLE };
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Groups.CONTENT_URI, projection, selection, selectionArgs, null);

        if (cursor.moveToFirst()){
            do {
                // Get the contact ID
                String groupId = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Groups._ID));

                // Get the contact's name
                String groupName = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Groups.TITLE));

                // Displaying contact information in log for debugging
                System.out.println("Group ID: " + groupId + ", Group Name: " + groupName);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}