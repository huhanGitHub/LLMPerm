import java.util.logging.*;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.JMX;
import java.lang.management.ManagementFactory;

public class LoggingMXBean_setLoggerLevel {

    public void test_LoggingMXBean_setLoggerLevel() {
        // Get the platform MBean server
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        // Construct the ObjectName for the LoggingMXBean
        ObjectName mxbeanName = null;
        try {
            mxbeanName = new ObjectName(LogManager.LOGGING_MXBEAN_NAME);
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        }

        // Get MXBean proxy instance
        LoggingMXBean proxy = null;
        try {
            proxy = JMX.newMXBeanProxy(mbs, mxbeanName, LoggingMXBean.class);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        // Test: set logger level
        try {
            proxy.setLoggerLevel("global", "INFO");
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        // Verify: get logger level
        String level = proxy.getLoggerLevel("global");
        assert (level != null);
        assert (level.equals("INFO"));
    }
}