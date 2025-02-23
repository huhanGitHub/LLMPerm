import android.provider.ContactsContract;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

public class Contacts_setPhotoData {

    public void test_Contacts_setPhotoData(Context context, String contactId, byte[] photoData) throws Exception {
        if (!checkWriteContactsPermission()) {
            throw new SecurityException("Write Contacts permission is not granted");
        }
        
        Uri rawContactPhotoUri = Uri.withAppendedPath(
                ContactsContract.Contacts.CONTENT_URI, contactId);
        ContentResolver contentResolver = context.getContentResolver();

        ContentValues values = new ContentValues();
        String where = ContactsContract.Data.RAW_CONTACT_ID + " = ? AND " +
                ContactsContract.Data.MIMETYPE + "=='" +
                ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE + "'";
        String[] args = new String[]{contactId};
        values.put(ContactsContract.CommonDataKinds.Photo.PHOTO, photoData);

        int rows = contentResolver.update(
                ContactsContract.Data.CONTENT_URI,
                values,
                where,
                args
        );
        
        if (rows != 0) {
            Log.i("Contact photo", "Contact photo changed");
        } else {
            Log.e("Contact photo", "No photo row found for this contact");
        }
    }

    // Illustrative purpose only
    private boolean checkWriteContactsPermission(){
        // Have your actual permission check logic here
        return true;
    }
}