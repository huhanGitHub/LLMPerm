import android.media.audiofx.AcousticEchoCanceler;
import android.os.Build;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
    
public class AcousticEchoCanceler_create {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void test_AcousticEchoCanceler_create() {
        AcousticEchoCanceler echoCanceler = AcousticEchoCanceler.create(0);

        if (echoCanceler != null) {
            echoCanceler.setEnabled(true);
            boolean isEnabled = echoCanceler.getEnabled();
            if (!isEnabled) {
                // handle error here
                Toast.makeText(this, "Failed to enable AcousticEchoCanceler.", Toast.LENGTH_LONG).show();
            }
        } else {
            // handle error here
            Toast.makeText(this, "Failed to create AcousticEchoCanceler.", Toast.LENGTH_LONG).show();
        }
    }
}