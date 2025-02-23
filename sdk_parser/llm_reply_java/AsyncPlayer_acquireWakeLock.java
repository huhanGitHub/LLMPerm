import android.content.Context;
import android.media.AudioManager;
import android.media.AsyncPlayer;
import android.net.Uri;
import android.os.PowerManager;

public class AsyncPlayer_acquireWakeLock {
  public void test_AsyncPlayer_acquireWakeLock() {
    // Get the PowerManager service
    PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);

    // Create a wake lock
    PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyApp::MyWakelockTag");

    // Acquire the wake lock
    wakeLock.acquire();

    // Create an AsyncPlayer
    AsyncPlayer asyncPlayer = new AsyncPlayer("AsyncPlayer");

    // Get AudioManager
    AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

    // Get the current media vol level
    int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

    // Set volume to max
    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);

    // Use the AsyncPlayer to play a sound from a Uri
    asyncPlayer.play(this, Uri.parse("android.resource://your.package.name/" + R.raw.your_sound_file), false, AudioManager.STREAM_MUSIC);

    // Release the wake lock
    wakeLock.release();
  }
}