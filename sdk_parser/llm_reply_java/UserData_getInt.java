public class UserData_getInt {
    public void test_UserData_getInt() {
        // Create a mock field type and value
        String fieldId = "user_password";
        String value = "1234";

        // Initialize the autofill field collection
        AutofillField autofillField = new AutofillField(fieldId, value);

        // Initialize a UserData builder with a dummy user id and name, we only care about the field value.
        UserData.Builder builder = new UserData.Builder("testId", "testName", "en_US");

        // Then populate it with our mock field
        builder.addField(autofillField.getId(), autofillField.getValue());

        // Build the user data
        UserData userData = builder.build();

        // Assuming getInt is a method which converts string to integer
        // Let's try to retrieve the password in integer
        int password = userData.getInt(fieldId);

        // If getInt works correctly, and it converts the string "1234" to integer 1234.
        // This assert function should pass
        assert(password == 1234);
    }
}