import java.io.File;
import java.io.FileInputStream;
import java.util.prefs.Preferences;

public class Preferences_importPreferences {
    public void test_Preferences_importPreferences() {
        try {
            FileInputStream fis = new FileInputStream(new File("<xml_file>"));
            Preferences.importPreferences(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}