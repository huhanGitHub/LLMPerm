public class LogManager_addPropertyChangeListener {
    public void test_LogManager_addPropertyChangeListener() {
        // Prepare a new instance of LogManager
        LogManager logManager = LogManager.getLogManager();

        // Create a new PropertyChangeListener
        PropertyChangeListener pcl = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("Change occurred in: " + evt.getPropertyName());
            }
        };

        try {
            // Add the PropertyChangeListener to logManager
            logManager.addPropertyChangeListener(pcl);

            // Get the current log level
            System.out.println(logManager.getLevel());

            // Change the log level
            logManager.setLevel(Level.SEVERE);

            // Get the current log level (should be new changed level)
            System.out.println(logManager.getLevel());

        } catch (SecurityException e) {
            e.printStackTrace();
        } finally {
            // Remove listener
            logManager.removePropertyChangeListener(pcl);
        }
    }
}