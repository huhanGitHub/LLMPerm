public class Contacts_addToGroup {

    private void test_Contacts_addToGroup() {
        ContactsContract.Groups _Group = new ContactsContract.Groups(ContactsContract.Groups.CONTENT_URI, getContentResolver());
        Long _GroupId = 1L; // replace this with actual group id
        Long _ContactId = 1L; // replace this with actual contact id

        if (!_Group.AddToGroup(_ContactId, _GroupId))
            Toast.makeText(this, "Add to group operation failed", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Contact added successfully to group", Toast.LENGTH_LONG).show();
    }
}