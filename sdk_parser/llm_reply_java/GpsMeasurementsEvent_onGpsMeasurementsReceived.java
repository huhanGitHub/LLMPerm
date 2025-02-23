public class GpsMeasurementsEvent_onGpsMeasurementsReceived {

    public void test_GpsMeasurementsEvent_onGpsMeasurementsReceived() {
        // Initialize Location Manager
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Check for GPS Provider
        if (locationManager == null) {
            throw new RuntimeException("LocationManager is null");
        }

        // Create GPS Status Listener to capture GPS Measurement Events
        GpsStatus.Listener listener = new GpsStatus.Listener() {
            @Override
            public void onGpsStatusChanged(int event) {
                if (event == GpsStatus.GPS_EVENT_SATELLITE_STATUS) {
                    GpsStatus gpsStatus = locationManager.getGpsStatus(null);
                    if (gpsStatus != null) {
                        Iterable<GpsSatellite> satellites = gpsStatus.getSatellites();
                        // Loop through satellites to process measurement details
                        for (GpsSatellite sat : satellites) {
                            float azimuth = sat.getAzimuth();
                            float elevation = sat.getElevation();
                            int prn = sat.getPrn();
                            float snr = sat.getSnr(); 

                            // Log satellite details for testing
                            Log.d("GPS_TEST", "Satellite PRN: " + prn + ", Azimuth: " + azimuth + ", Elevation: " + elevation + ", SNR: " + snr);
                        }
                    } else {
                        Log.d("GPS_TEST", "GPS Status is null");
                    }
                }
            }
        };

        // Register for GPS Status updates 
        try {
            locationManager.addGpsStatusListener(listener);
        } catch (SecurityException ex) {
            Log.e("GPS_TEST", "Permission for GPS not granted", ex);
        }
    }
}