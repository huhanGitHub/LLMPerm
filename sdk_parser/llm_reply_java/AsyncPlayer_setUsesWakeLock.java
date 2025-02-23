import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AsyncPlayer;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class AsyncPlayer_setUsesWakeLock {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void test_AsyncPlayer_setUsesWakeLock() {
        Context context = getApplicationContext();

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WAKE_LOCK)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WAKE_LOCK}, 1);
        } else {
            AsyncPlayer asyncPlayer = new AsyncPlayer("test");
            PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
            PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "app:MyWakeLock");
            asyncPlayer.setUsesWakeLock(wakeLock);

            if(wakeLock.isHeld()){
                Log.d("test_AsyncPlayer", "WakeLock is held");
            } else {
                Log.d("test_AsyncPlayer", "WakeLock is not held");
            }

            asyncPlayer.stop();
            if(wakeLock.isHeld()){
                wakeLock.release();
            }
        }
    }
}