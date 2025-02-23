public class MediaRoute2ProviderService_transferToRoute {

    public void test_MediaRoute2ProviderService_transferToRoute() {
        final String oldRouteId = "oldRouteId";
        final String newRouteId = "newRouteId";
        
        MediaRoute2ProviderService mediaRoute2ProviderService = new MediaRoute2ProviderService() {
            @Override
            public void onTransferToRoute(String oldRouteId, String newRouteId) {
                super.onTransferToRoute(oldRouteId, newRouteId);
                // potentially some implementation logic...
            }

            @Nullable
            @Override
            public RouteDiscoveryPreference onGetRouteDiscoveryPreference() {
                return null;
            }

            @Nullable
            @Override
            public List<MediaRoute2Info> onGetRoutes() {
                return null;
            }
        };

        // Trying to mimic transfer
        mediaRoute2ProviderService.onTransferToRoute(oldRouteId, newRouteId);
    }
}