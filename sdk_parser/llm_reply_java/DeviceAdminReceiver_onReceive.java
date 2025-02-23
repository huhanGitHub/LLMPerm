public class DeviceAdminReceiver_onReceive extends DeviceAdminReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();

        if (ACTION_DEVICE_ADMIN_DISABLE_REQUESTED.equals(action)) {
            Toast.makeText(context, "Device Admin Disabled", Toast.LENGTH_SHORT).show();
        } else if (ACTION_DEVICE_ADMIN_ENABLED.equals(action)) {
            Toast.makeText(context, "Device Admin Enabled", Toast.LENGTH_SHORT).show();
        }
    }
}