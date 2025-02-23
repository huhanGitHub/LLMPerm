public class LogManager_removePropertyChangeListener {
    public void test_LogManager_removePropertyChangeListener() {
        // Define a new property change listener
        PropertyChangeListener listener = evt -> {
            // Handle the property change event here
            System.out.printf("Property %s has changed from %s to %s.\n",
                    evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
        };

        // Get the log manager instance
        LogManager logManager = LogManager.getLogManager();

        try {
            // Add the listener to the log manager
            logManager.addPropertyChangeListener(listener);

            // Perform some logging activities here....

            // Then remove the listener
            logManager.removePropertyChangeListener(listener);
        } catch (SecurityException e) {
            // Handle the error
            e.printStackTrace();
        }
    }
}