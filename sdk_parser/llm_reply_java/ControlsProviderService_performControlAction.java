public class ControlsProviderService_performControlAction {

    public void test_ControlsProviderService_performControlAction() {
        final String CONTROL_ID = "wifi_toggle";

        Control control = new Control.StatefulBuilder(CONTROL_ID, PendingIntent.getActivity(this, 0, new Intent(Settings.ACTION_WIFI_SETTINGS), 0))
                .setTitle("WiFi")
                .setSubtitle("Click to toggle")
                .setStructure(new StructureInfo(CONTROL_ID, "WiFi", new ArrayList<>()))
                .setDeviceType(DeviceTypes.TYPE_UNKNOWN)
                .setStatus(Control.STATUS_OK)
                .setControlTemplate(new ToggleTemplate(CONTROL_ID, new ControlButtonState(true, "description", Color.BLUE)))
                .build();

        Command command = new Command.RequestState(CONTROL_ID);

        ControlsProviderService.performControlAction(CONTROL_ID, control, command, new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                if (integer == ControlAction.RESPONSE_OK) {
                    WifiManager wifiManager = getWifiManagerService();
                    wifiManager.setWifiEnabled(!wifiManager.isWifiEnabled());
                }
            }
        });
    }

    private WifiManager getWifiManagerService() {
        return (WifiManager) getSystemService(Context.WIFI_SERVICE);
    }
}