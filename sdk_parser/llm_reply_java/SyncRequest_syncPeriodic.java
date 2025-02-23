public class SyncRequest_syncPeriodic {
    public void test_SyncRequest_syncPeriodic() {
        // Instantiate Account
        Account account = new Account("dummyaccount", "com.example");

        // Content provider authority
        String authority = "com.example.provider";

        // Sync interval in seconds
        long SYNC_INTERVAL = 60L; // 1 minute

        // Initialize a SyncRequest builder
        SyncRequest.Builder builder = new SyncRequest.Builder();

        // Set periodic sync
        builder.syncPeriodic(SYNC_INTERVAL, SYNC_INTERVAL / 3); // Flex time is SYNC_INTERVAL / 3

        // Set the account and authority for the SyncRequest 
        builder.setSyncAdapter(account, authority);

        // Disable syncing over mobile networks
        builder.setDisallowMetered(false);

        // Create SyncRequest
        SyncRequest request = builder.build();

        // Request sync
        ContentResolver.syncAdaptersOfType(request);

        // Check if sync is defined correctly
        Bundle extras = new Bundle();
        extras.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        extras.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        
        assertTrue(ContentResolver.getSyncAutomatically(account, authority));
        assertFalse(ContentResolver.getMasterSyncAutomatically());
        ContentResolver.requestSync(account, authority, extras);
    }
}