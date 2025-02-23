import android.util.Log;
import java.util.logging.Logger;

public class Logger_setParent {
    public void test_Logger_setParent() {
        try {
            // Create two logger instances
            Logger logger1 = Logger.getLogger("logger1");
            Logger logger2 = Logger.getLogger("logger2");

            // Set logger1 as parent of logger2
            logger2.setParent(logger1);

            // Get parent of logger2 and check if it is logger1
            if(logger2.getParent() == logger1) {
                Log.d("LoggerTest", "test_Logger_setParent PASSED");
            } else {
                Log.d("LoggerTest", "test_Logger_setParent FAILED");
            }
        } catch (Exception e) {
            Log.e("LoggerTest", "Exception in test_Logger_setParent", e);
        }
    }
}