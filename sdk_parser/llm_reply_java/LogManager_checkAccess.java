public class LogManager_checkAccess {
    public void test_LogManager_checkAccess() {
        LogManager logManager = LogManager.getLogManager();
        try {
            logManager.checkAccess();
        } catch (SecurityException e) {
            Log.d("ActivityLog", "Insufficient permissions to alter logging configuration " + e.getMessage());
        }
    }
}