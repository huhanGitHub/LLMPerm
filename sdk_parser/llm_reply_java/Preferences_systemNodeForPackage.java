import java.util.prefs.Preferences;
import java.util.prefs.BackingStoreException;

public class Preferences_systemNodeForPackage {
    public void test_Preferences_systemNodeForPackage() {
        try {
            // Access system root preference
            Preferences systemPref = Preferences.systemRoot();

            // Access system node for a package
            Preferences systemNodePkg = Preferences.systemNodeForPackage(this.getClass());

            // It's a good practice to check if node is already there
            if(systemNodePkg != null) {
                // Store key-value pair
                systemNodePkg.put("key1", "value1");

                // Retrieve value using key
                String value = systemNodePkg.get("key1", "defaultValue");
                System.out.println("Key1: " + value);

                // Remove key-value
                systemNodePkg.remove("key1");

                // Flush ensures changes are made permanent
                systemNodePkg.flush();
            }

        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
    }
}