public class BlockedNumberContract_isBlocked {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void test_BlockedNumberContract_isBlocked(Context context) {
        if (ContextCompat.checkSelfPermission(context, 
            Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {

            // Content Resolver
            ContentResolver contentResolver = context.getContentResolver();

            try {
                // Uri
                Uri uri = BlockedNumberContract.BlockedNumbers.CONTENT_URI;

                // Query
                Cursor cursor = contentResolver.query(uri, null, null, null, null);

                // Iterate through all blocked numbers
                while (cursor.moveToNext()) {
                    // Is blocked
                    boolean isBlocked = (cursor.getInt(cursor.getColumnIndex(
                        BlockedNumberContract.BlockedNumbers.COLUMN_ORIGINAL_NUMBER)) == 1);
                    Log.d("BlockedNumberContract", "Is blocked: " + isBlocked);
                }

                cursor.close();
            } catch (Exception e) {
                Log.e("BlockedNumberContract", "Error: " + e.getMessage());
            }
        }
        else {
            Log.e("BlockedNumberContract", 
                "Permission not granted! Request READ_CONTACTS permission to get blocked numbers.");
        }
    }
}