import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class GpsStatus_getSatellites {

    private void test_GpsStatus_getSatellites() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager != null) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    GpsStatus gpsStatus = locationManager.getGpsStatus(null);
                    Iterable<GpsSatellite> satellites = gpsStatus.getSatellites();
                    int satCount = 0;
                    for (GpsSatellite satellite : satellites) {
                        if (satellite.usedInFix()) {
                            satCount++;
                        }
                    }
                    Log.v("GpsStatus","Number of used satellites: " + satCount);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    // not used
                }

                @Override
                public void onProviderEnabled(String provider) {
                    // not used
                }

                @Override
                public void onProviderDisabled(String provider) {
                    // not used
                }
            });
        } else {
            Log.e("GpsStatus", "Location manager is null.");
        }
    }
}