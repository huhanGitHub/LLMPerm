import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.InputStream;

public class Contacts_openContactPhotoInputStream {

    public void test_Contacts_openContactPhotoInputStream(Context context, long contactId) {
        try {
            Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
            InputStream photoDataStream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(), contactUri);
            
            if(photoDataStream != null) {
                Bitmap photo = BitmapFactory.decodeStream(photoDataStream);
                if(photo != null) {
                    // use photo here
                } else {
                    Log.d("PhotoTest", "Failure decoding photo");
                }
            } else {
                Log.d("PhotoTest", "Failure fetching photo data");
            }
        } catch (Exception e) {
            Log.e("PhotoTest", "Error fetching contact photo", e);
        }
    }
}