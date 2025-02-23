import android.media.AudioRecord;
import android.media.audiofx.NoiseSuppressor;
import android.util.Log;

public class NoiseSuppressor_create {

    public void test_NoiseSuppressor_create() {
        // You should have AudioRecord object ready.
        // Assuming 'audioRecord' is a valid initiated AudioRecord object.
        AudioRecord audioRecord = getAudioRecord(); // replace getAudioRecord() with your actual AudioRecord object provider.

        if(NoiseSuppressor.isAvailable()){
            NoiseSuppressor noiseSuppressor = NoiseSuppressor.create(audioRecord.getAudioSessionId());
            if(noiseSuppressor != null) {
                noiseSuppressor.setEnabled(true);
                if(!noiseSuppressor.getEnabled()){
                    Log.e("test_NoiseSuppressor", "Failed to enable noise suppressor.");
                }
                else{
                    // Noise suppressor enabled successfully.
                    Log.i("test_NoiseSuppressor", "Noise suppressor enabled successfully.");
                }
            }
            else {
                Log.e("test_NoiseSuppressor", "Failed to create noise suppressor.");
            }
        }
        else {
            Log.e("test_NoiseSuppressor", "Noise suppressor is not available on this device.");
        }
    }

    private AudioRecord getAudioRecord() {
        // your method to provide the AudioRecord
        return null;  // replace this with your actual method to get an AudioRecord object.
    }
}