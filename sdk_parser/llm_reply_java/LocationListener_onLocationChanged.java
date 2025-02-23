public class LocationListener_onLocationChanged {
    public void test_LocationListener_onLocationChanged() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Instantiate a LocationListener
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // This callback will execute every time user's location changes.
                // You can add your logic here to use the new location data.

                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                Log.i("Location Listener", "Location has changed: Lat: " + latitude + ", Lon: " + longitude);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // This callback is called when the status of a provider changes.
            }

            @Override
            public void onProviderEnabled(String provider) {
                // This is called when a user has enabled a provider.
            }

            @Override
            public void onProviderDisabled(String provider) {
                // This is called when a user has disabled a provider.
            }
        };

        // Request location updates with appropriate permissions 
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
        }
    }
}