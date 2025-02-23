public class VoicemailContract_insert {
    private void test_VoicemailContract_insert() {
        ContentResolver contentResolver = getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Voicemails.SOURCE_PACKAGE, getPackageName());
        contentValues.put(Voicemails.DATE, System.currentTimeMillis());
        contentValues.put(Voicemails.HAS_CONTENT, 1);
        contentValues.put(Voicemails.NUMBER, "+123456789");
        contentValues.put(Voicemails.DURATION, 60);
        contentValues.put(Voicemails.TRANSCRIPTION, "Transcription text");

        try {
            Uri uri = contentResolver.insert(VoicemailContract.Voicemails.CONTENT_URI, contentValues);
            if (uri != null) {
                Toast.makeText(this, "Voicemail inserted successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Voicemail insert failed", Toast.LENGTH_LONG).show();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Toast.makeText(this, "Voicemail insert failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}