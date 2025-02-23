import java.sql.DriverManager;
import java.io.FileOutputStream;
import java.io.PrintStream;
import android.util.Log;

public class DriverManager_setLogStream {
    public void test_DriverManager_setLogStream() {
        try {
            // Uses the deprecated setLogStream method 
            PrintStream logStream = new PrintStream(new FileOutputStream("/sdcard/log.txt"));
            DriverManager.setLogStream(logStream);
            
            // Do some actions that will trigger logs
            // For instance: DriverManager.getConnection(/* your JDBC URL here */);

        } catch(Exception e) {
            Log.e("TestApp", "Failed to set Log Stream", e);
        }
    }
}