import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverManager_setLogWriter {
    public void test_DriverManager_setLogWriter() {
        try {
            PrintWriter pw = new PrintWriter("your-log-file-here.txt");
            DriverManager.setLogWriter(pw);

            // make some database operations for which the logs will be written in the defined file...

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}