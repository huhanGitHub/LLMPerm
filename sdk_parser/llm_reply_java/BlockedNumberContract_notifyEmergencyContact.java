public class BlockedNumberContract_notifyEmergencyContact {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void test_BlockedNumberContract_notifyEmergencyContact() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLOCKED_NUMBER_STORE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLOCKED_NUMBER_STORE}, 0);
        } else {
            ContentResolver contentResolver = getContentResolver();

            ContentValues contentValues = new ContentValues();
            contentValues.put(BlockedNumberContract.BlockedNumbers.COLUMN_ORIGINAL_NUMBER, "123456789");
            contentValues.put(BlockedNumberContract.BlockedNumbers.COLUMN_E164_NUMBER, "123456789");

            try {
                Uri uri = contentResolver.insert(BlockedNumberContract.BlockedNumbers.CONTENT_URI, contentValues);
                Cursor cursor = contentResolver.query(uri, null, null, null, null);

                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(BlockedNumberContract.BlockedNumbers.COLUMN_ORIGINAL_NUMBER);
                    String number = cursor.getString(columnIndex);
                    Log.d("Test", "Blocked number is: " + number);
                    cursor.close();
                }
            } catch (Exception e) {
                Log.e("Test", "Error inserting blocked number", e);
            }
        }
    }
}