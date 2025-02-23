public class GnssMeasurementsEvent_onGnssMeasurementsReceived extends AppCompatActivity implements GnssMeasurementsEvent.Callback {
    final LocationManager locationManager;

    public GnssMeasurementsEvent_onGnssMeasurementsReceived() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    public void test_GnssMeasurementsEvent_onGnssMeasurementsReceived() {
        // Check if the device supports the GNSS Measurements.
        boolean isGnssMeasurementsSupported = locationManager.isGnssMeasurementsEnabled();

        // If supported, then register the callback.
        if (isGnssMeasurementsSupported) {
            locationManager.registerGnssMeasurementsCallback(new Executor() {
                @Override
                public void execute(Runnable command) {
                    command.run();
                }
            }, this);
        }
    }

    @Override
    public void onGnssMeasurementsReceived(GnssMeasurementsEvent eventArgs) {
        // Log or use the GNSS Measurements results here
        System.out.println("GnssMeasurements received: " + eventArgs.toString());
    }

    @Override
    public void onStatusChanged(int status) {
        // Handle status change
        System.out.println("GnssMeasurements status changed: " + status);
    }
}