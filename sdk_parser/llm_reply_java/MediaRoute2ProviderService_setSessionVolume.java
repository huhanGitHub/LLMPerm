public class MediaRoute2ProviderService_setSessionVolume {

    public void test_MediaRoute2ProviderService_setSessionVolume() {
        MediaRoute2Provider mediaRoute2Provider = new MediaRoute2Provider(
                new MediaRoute2Provider.Callback() {
                    @Override
                    public void onSetSessionVolume(String sessionId, int volume) {
                        super.onSetSessionVolume(sessionId, volume);
                        // set the session volume
                    }

                    @Override
                    public void onSetSessionVolumeBy(String sessionId, int delta) {
                        super.onSetSessionVolumeBy(sessionId, delta);
                        // modify the session volume
                    }
                },
                new MediaRoute2ServiceDescriptor.Builder("media_route_service").build(), this /* context */
        ) {
            @Override
            public MediaRoute2ProviderService onCreateBinder() {
                return super.onCreateBinder();
            }
        };
    }
}