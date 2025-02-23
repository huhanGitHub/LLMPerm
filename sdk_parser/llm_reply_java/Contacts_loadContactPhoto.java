public class Contacts_loadContactPhoto {

    public void test_Contacts_loadContactPhoto() {
        // The contact id which you want to retrieve photo. This can be retrieved from the Contacts Contract api
        long contactId = 0;
    
        // Making use of ContactsContract api to load contact photo
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);

        Cursor cursor = getContentResolver().query(photoUri,
            new String[] {ContactsContract.Contacts.Photo.PHOTO}, null, null, null);
        if (cursor == null) {
            // there's nothing to show
            return;
        }
        try {
            if (cursor.moveToFirst()) {
                byte[] data = cursor.getBlob(0);
                if (data != null) {
                    // convert byte array to Bitmap to be displayed in an ImageView
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    ImageView imageView = new ImageView(this);
                    imageView.setImageBitmap(bitmap);
                }
            }
        } finally {
            cursor.close();
        }
    }
}