import android.app.Activity;
import android.app.job.JobParameters;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class JobParameters_getTriggeredContentUris extends Activity {
    
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void test_JobParameters_getTriggeredContentUris(JobParameters params) {
        Uri[] triggeredUris = params.getTriggeredContentUris();
        if (triggeredUris != null) {
            for (Uri uri : triggeredUris) {
                Log.i("JobParameters_getTriggeredContentUris", "Triggered Uri: " + uri.toString());
                Toast.makeText(this, "Triggered Uri: " + uri.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
}