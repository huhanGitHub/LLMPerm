public class GpsMeasurement_getCn0InDbHz {
    public void test_GpsMeasurement_getCn0InDbHz() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.addGpsStatusListener(new GpsStatus.Listener() {
                @Override
                public void onGpsStatusChanged(int event) {
                    if (event == GpsStatus.GPS_EVENT_SATELLITE_STATUS) {
                        GpsStatus gpsStatus = locationManager.getGpsStatus(null);
                        Iterable<GpsMeasurement> measurements = gpsStatus.getSatelliteStatus().getMeasurements();

                        for (GpsMeasurement measurement : measurements) {
                            Log.d(TAG, "Cn0InDbHz: " + measurement.getCn0InDbHz());
                        }
                    }
                }
            });
        } else {
            Log.d(TAG, "GPS is not enabled");
        }
    }
}