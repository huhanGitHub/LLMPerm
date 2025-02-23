public class ConnectionMigrationOptions_setAllowNonDefaultNetworkUsage {

    private void test_ConnectionMigrationOptions_setAllowNonDefaultNetworkUsage() {
        try {
            // Create instance of ConnectionMigrationOptions.Builder
            ConnectionMigrationOptions.Builder builder = new ConnectionMigrationOptions.Builder();

            // Set allow non default network usage
            builder.setAllowNonDefaultNetworkUsage(true);

            // Build Options
            ConnectionMigrationOptions options = builder.build();

            // Check if it is set correctly
            if (options.getAllowNonDefaultNetworkUsage()) {
                Log.i("Test", "Allow non default network usage is successfully set to true");
            } else {
                Log.i("Test", "Failed to set allow non default network usage");
            }

        } catch (Exception ex) {
            // Print stack trace if exception occurs
            ex.printStackTrace();
            Log.e("Test", "Error: " + ex.getMessage());
        }
    }
}