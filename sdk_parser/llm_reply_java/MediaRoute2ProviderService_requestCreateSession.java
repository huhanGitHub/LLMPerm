public class MediaRoute2ProviderService_requestCreateSession {
    public void test_MediaRoute2ProviderService_requestCreateSession() {
        MediaRoute2ProviderService mediaRoute2ProviderService = new MediaRoute2ProviderService() {

            @Override
            public MediaRoute2Provider onCreateMediaRoute2Provider() {

                return new MediaRoute2Provider(getApplicationContext()) {

                    @Override
                    public void onDiscoveryRequestChanged(Bundle bundle) {
                        // your code here
                    }

                    @Override
                    public void onCreateRouteController(String routeId, String routeGroupId, int controllerId) {
                        // your code here
                    }

                    @Override
                    public void onSelectRoute(String routeId) {
                        this.requestCreateSession(new MediaRoute2Info.Builder(routeId, "Default route").build(), "testControlRequestId");
                    }
                };
            }
        };
    }
}