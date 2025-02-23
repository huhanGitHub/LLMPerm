import android.media.MediaController2;
import android.media.MediaSession2;
import android.media.MediaSession2.ControllerInfo;
import android.support.v4.media.MediaBrowserCompat;
import androidx.annotation.NonNull;

public class MediaController2_requestConnectToService {

    public void test_MediaController2_requestConnectToService() {
    
        MediaSession2 mediaSession2 = new MediaSession2.Builder(this).build();
    
        mediaSession2.setSessionCallback(new MediaSession2.SessionCallback() {
    
            @NonNull
            @Override
            public MediaSession2.SessionCommandGroup onConnect(@NonNull MediaSession2 session,
                    @NonNull ControllerInfo controller) {
                if (controller.getPackageName().equals(getPackageName())) {
                    return super.onConnect(session, controller);
                }
                return null;
            }
    
            @Override
            public void onPlay(@NonNull MediaSession2 session,
                    @NonNull MediaController2 controller) {
                super.onPlay(session, controller);
                // Add your code here to handle the play request
            }
        });
    
        MediaController2 mediaController2 = new MediaController2.Builder(this)
                .setSessionToken(mediaSession2.getToken())
                .setControllerCallback(new MediaController2.ControllerCallback() {
    
                    @Override
                    public void onConnected(@NonNull MediaController2 controller,
                            @NonNull MediaController2.PlaybackInfo info) {
                        super.onConnected(controller, info);
                        // Add your code here to handle the connection
                    }
    
                    @Override
                    public void onPlayerStateChanged(@NonNull MediaController2 controller,
                            int state) {
                        super.onPlayerStateChanged(controller, state);
                        // Add your code here to handle player state changes
                    }
                })
                .build();
    }
}