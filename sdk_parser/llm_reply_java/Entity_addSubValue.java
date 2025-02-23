import android.content.Entity;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.ContactsContract;

public class Entity_addSubValue {

    public void test_Entity_addSubValue() {
        // First, build a ContentValues object with some data
        ContentValues values = new ContentValues();
        values.put(ContactsContract.CommonDataKinds.Email.DATA, "test@example.com");
        values.put(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK);

        // Create Uri for the raw contact
        Uri rawContactUri = ContactsContract.RawContacts.CONTENT_URI;
        
        // Create an Entity object
        Entity entity = new Entity(values);

        // Add subvalue to the entity
        ContentValues subValues = new ContentValues();
        subValues.put(ContactsContract.Data.DATA1, "555-555-5555");
        subValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        entity.addSubValue(rawContactUri, subValues);

        // Now 'entity' would contain a subValue referring to a phone number
    }
}