import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.content.Context;

public class Ringtone_playFallbackRingtone {
    public void test_Ringtone_playFallbackRingtone() {
        try {
            // Obtain a Uri for our fallback ringtone
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            // If the user has a different preference, attempt to obtain that
            if (notification == null) {
                notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }

            // If we've correctly obtained a Uri, try to play it as a fallback
            if (notification != null) {
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}