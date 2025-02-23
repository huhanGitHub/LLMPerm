public class LogManager_reset {
    public void test_LogManager_reset() {
        try {
            // Save the original LogManager
            LogManager original = LogManager.getLogManager();

            // Substitute LogManager with a new instance
            LogManager newLogManager = LogManager.getLogManager().getClass().newInstance(); 
            System.setProperty("java.util.logging.manager", newLogManager.getClass().getName());
            Field field = LogManager.class.getDeclaredField("manager");
            field.setAccessible(true);
            field.set(null, newLogManager);

            // Check if LogManager is the new instance
            if (!LogManager.getLogManager().equals(newLogManager)) {
                throw new RuntimeException("Failed to substitute LogManager");
            }

            // Resetting LogManager 
            newLogManager.reset();

            // Restore original LogManager
            System.setProperty("java.util.logging.manager", original.getClass().getName());
            field.set(null, original);

        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException("Failed to reset LogManager", e);
        }
    }
}