import android.util.Log;
import java.util.prefs.Preferences;
import java.util.prefs.BackingStoreException;

public class Preferences_userRoot {

    public void test_Preferences_userRoot() {
        // Get the root preference node that is shared by all users
        Preferences rootPreferences = Preferences.userRoot();

        // Use it to store a preference value
        String name = "testName";
        String value = "testValue";
        rootPreferences.put(name, value);

        // Try to retrieve the value you just stored
        String retrievedValue = rootPreferences.get(name, "default");

        // Log the retrieved value for debugging purposes
        Log.d("MyApp", "Retrieved value: " + retrievedValue);

        // Print the sub tree
        try {
            for (String key : rootPreferences.keys()) {
                String prefValue = rootPreferences.get(key, null);
                Log.d("MyApp", "Key: " + key + ", Value: " + prefValue);
            }
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
    }

}