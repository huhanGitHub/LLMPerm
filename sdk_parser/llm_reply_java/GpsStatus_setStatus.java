public class GpsStatus_setStatus {
    public void test_GpsStatus_setStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        
        if (locationManager != null) {
            // Checks if GPS provider is enabled
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (!isGPSEnabled) {
                // Request user to enable GPS
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            } else {
                // GPS is enabled, do your work here
            }
        } else {
            Log.e("GPSStatus", "Location Manager is null");
        }
    }
}