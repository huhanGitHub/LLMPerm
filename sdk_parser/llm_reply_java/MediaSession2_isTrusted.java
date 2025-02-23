public class MediaSession2_isTrusted {
  
    public boolean test_MediaSession2_isTrusted(MediaSession2 mediaSession) {
        try {
            if (mediaSession == null || !mediaSession.isActive() || mediaSession.getCurrentMediaItem() == null) {
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error checking MediaSession2 trust status.");
            return false;
        }
        return true;
    }
}