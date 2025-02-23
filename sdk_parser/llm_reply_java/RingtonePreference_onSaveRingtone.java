public class RingtonePreference_onSaveRingtone {
    
    public void test_RingtonePreference_onSaveRingtone() {

        RingtonePreference ringtonePreference = new RingtonePreference(this);
        ringtonePreference.setKey("my_ringtone_preference");
        ringtonePreference.setTitle("Select Ringtone");

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String ringtonePath = sharedPrefs.getString("my_ringtone_preference", null);

        // Save the ringtone
        Uri ringtoneUri;
        if (ringtonePath != null) {
            ringtoneUri = Uri.parse(ringtonePath);
        } else {
            // Use default ringtone
            ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        }

        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("my_ringtone_preference", ringtoneUri.toString());
        editor.commit();  // apply asynchronously

        // Validate
        String savedRingtonePath = sharedPrefs.getString("my_ringtone_preference", null);
        Uri savedRingtoneUri = Uri.parse(savedRingtonePath);
        
        Ringtone savedRingtone = RingtoneManager.getRingtone(this, savedRingtoneUri);
        
        // Check the saved ringtone
        if (savedRingtone != null) {
            Toast.makeText(this, "Saved ringtone: " + savedRingtone.getTitle(this), 
                Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to save ringtone", Toast.LENGTH_SHORT).show();
        }
    }
}