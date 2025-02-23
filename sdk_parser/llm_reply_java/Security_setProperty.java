import java.security.Security;
import android.util.Log;

public class Security_setProperty {
    public void test_Security_setProperty() {
        try {
            // set the property
            Security.setProperty("securerandom.source", "SHA1PRNG");
            
            // get the property
            String property = Security.getProperty("securerandom.source");
            
            // print the property value
            Log.d("SecurityTest", "securerandom.source: " + property);
            
            // check if the property is set correctly
            if (!"SHA1PRNG".equals(property)) {
                throw new Exception("Failed to set security property");
            }
            
        } catch (Exception e) {
            // handle exception
            Log.e("SecurityTest", "Exception", e);
        }
    }
}