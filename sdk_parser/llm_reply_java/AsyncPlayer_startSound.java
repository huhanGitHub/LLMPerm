import android.content.Context;
import android.media.AsyncPlayer;
import android.media.AudioAttributes;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

public class AsyncPlayer_startSound extends AppCompatActivity {

    private AsyncPlayer asyncPlayer;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        asyncPlayer = new AsyncPlayer("TestTag");
        test_AsyncPlayer_startSound("https://your-uri/sample.mp3");
    }

    private void test_AsyncPlayer_startSound(String soundUri) {
        Uri uri = Uri.parse(soundUri);
        AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).build();

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
            asyncPlayer.play(this, uri, false, audioAttributes);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.INTERNET }, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Internet permission granted!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Internet permission denied!", Toast.LENGTH_SHORT).show(); 
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        asyncPlayer.stop();
    }
}