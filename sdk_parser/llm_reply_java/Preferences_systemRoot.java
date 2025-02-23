import java.util.prefs.Preferences;

public class Preferences_systemRoot {

    public void test_Preferences_systemRoot() {
        // Getting system preferences
        Preferences systemPrefs = Preferences.systemRoot();
        
        // Getting user preferences
        Preferences userPrefs = Preferences.userRoot();

        // Put some value in system preferences
        systemPrefs.put("SystemKey", "SystemValue");
        System.out.println("SystemKey: " + systemPrefs.get("SystemKey", "DefaultValue"));

        // Put some value in user preferences
        userPrefs.put("UserKey", "UserValue");
        System.out.println("UserKey: " + userPrefs.get("UserKey", "DefaultValue"));
    }
}