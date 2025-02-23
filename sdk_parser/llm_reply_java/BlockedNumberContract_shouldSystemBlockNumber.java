public class BlockedNumberContract_shouldSystemBlockNumber {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void test_BlockedNumberContract_shouldSystemBlockNumber(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_BLOCKED_NUMBERS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_BLOCKED_NUMBERS}, 1);
        } else {
            checkBlockedNumber(activity, "1234567890");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void checkBlockedNumber(Context context, String phoneNumber) {
        Uri blockedNumbersUri = BlockedNumberContract.BlockedNumbers.CONTENT_URI;
        String[] projection = new String[]{BlockedNumberContract.BlockedNumbers.COLUMN_ORIGINAL_NUMBER};
        String selection = BlockedNumberContract.BlockedNumbers.COLUMN_ORIGINAL_NUMBER + " = ?";
        String[] selectionArgs = new String[]{phoneNumber};

        try (Cursor cursor = context.getContentResolver().query(blockedNumbersUri, projection, selection, selectionArgs, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                System.out.println("Number is system blocked.");
            } else {
                System.out.println("Number is not system blocked.");
            }
        } catch (SecurityException e) {
            System.out.println("Permission Required.");
        }
    }
}