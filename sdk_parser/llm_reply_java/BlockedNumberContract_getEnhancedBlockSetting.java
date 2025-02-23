public class BlockedNumberContract_getEnhancedBlockSetting {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void test_BlockedNumberContract_getEnhancedBlockSetting() {

        // Check if permissions are granted
        if (checkSelfPermission(Manifest.permission.READ_BLOCKED_NUMBERS) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.WRITE_BLOCKED_NUMBERS) != PackageManager.PERMISSION_GRANTED) {

            // Request permissions
            requestPermissions(new String[]{Manifest.permission.READ_BLOCKED_NUMBERS, Manifest.permission.WRITE_BLOCKED_NUMBERS}, 1);
        } else {

            // Get Content Resolver
            ContentResolver contentResolver = getContentResolver();

            Uri enhancedBlockUri = BlockedNumberContract.BlockedNumbers.ENHANCED_SETTING_CONTENT_URI;

            try {
                Cursor cursor = contentResolver.query(enhancedBlockUri, new String[]{BlockedNumberContract.BlockedNumbers.COLUMN_ID},
                        null, null, null);

                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        int enhancedBlockingEnabled = cursor.getInt(cursor.getColumnIndex(BlockedNumberContract.BlockedNumbers.COLUMN_E164_NUMBER));
                        Toast.makeText(this, "Enhanced Blocking Enabled : " + enhancedBlockingEnabled, Toast.LENGTH_LONG).show();
                    }
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}