public class VoicemailContract_deleteAll {
    public void test_VoicemailContract_deleteAll() {
        // Get the content resolver
        ContentResolver contentResolver = getContentResolver();

        // Define the selection and selection arguments for the delete operation
        String selection = VoicemailContract.Voicemails._ID + "=?";
        String[] selectionArgs = {"1"}; // replace "1" with the specific voicemail ID that you want to delete

        // The delete operation is performed on the Voicemails content URI
        Uri voicemailsUri = VoicemailContract.Voicemails.CONTENT_URI;

        try {
            // Execute the delete operation
            int numRowsDeleted = contentResolver.delete(voicemailsUri, selection, selectionArgs);

            // Check the number of rows deleted
            if (numRowsDeleted > 0) {
                Log.d("VoicemailContract", "Successfully deleted " + numRowsDeleted + " voicemails.");
            } else {
                Log.d("VoicemailContract", "No voicemails were deleted.");
            }
        } catch (Exception e) {
            // If an exception occurs, log an error message
            Log.e("VoicemailContract", "Error deleting voicemail", e);
        }
    }
}