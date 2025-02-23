import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

public class Ringtone_getTitle {

    public void test_Ringtone_getTitle() {
        // Obtain the default ringtone URI
        Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
      
        // Get a Ringtone object
        Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), ringtoneUri);
      
        // Get and print the title of the ringtone 
        String title = ringtone.getTitle(this);
    
        //Display the title
        Toast.makeText(getApplicationContext(), "Ringtone Title: " + title, Toast.LENGTH_LONG).show();
    }
}