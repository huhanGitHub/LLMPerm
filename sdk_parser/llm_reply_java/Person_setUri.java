import android.app.Person;
import android.net.Uri;
import android.widget.Toast;

public class Person_setUri {
    public void test_Person_setUri() {
        try {
            // Construct the URI for the user's profile picture
            String profilePictureUri = "https://example.com/path/to/profile/image";

            // Create a new Person instance using the Person.Builder class
            Person user = new Person.Builder()
                    .setUri(Uri.parse(profilePictureUri))
                    .setName("Jane Doe")
                    .build();

            // Output user's name and uri to check if the uri is set
            Toast.makeText(this, "User's name: " + user.getName() + ", User's uri: " + user.getUri(),
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Failed to set user's uri with an error: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}