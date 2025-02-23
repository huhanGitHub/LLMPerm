public class BlockedNumberContract_unblock {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void test_BlockedNumberContract_unblock() {
        if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, 1);
        } else {
            unblockNumber("5555555555");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void unblockNumber(String phoneNumber) {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = BlockedNumberContract.BlockedNumbers.CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, new String[]{BlockedNumberContract.BlockedNumbers._ID},
                BlockedNumberContract.BlockedNumbers.COLUMN_ORIGINAL_NUMBER + "=?",
                new String[]{phoneNumber}, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(BlockedNumberContract.BlockedNumbers._ID));
                contentResolver.delete(ContentUris.withAppendedId(BlockedNumberContract.BlockedNumbers.CONTENT_URI, id), null, null);
            }
            cursor.close();
        }

        Toast.makeText(this, "Number unblocked successfully", Toast.LENGTH_SHORT).show();
    }
}