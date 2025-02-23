public class Geocoder_getFromLocation {
    public void test_Geocoder_getFromLocation() {
        double latitude = 40.7143528; // example latitude
        double longitude = -74.0059731; // example longitude
        int maxResults = 1;

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, maxResults);
            // if an address is found, print it
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                String result = "Address: " + address.getAddressLine(0);
                Log.d("MyActivity", "test_Geocoder_getFromLocation result: " + result);
            } else {
                Log.d("MyActivity", "No address found");
            }
        } catch (IOException e) {
            Log.e("MyActivity", "Cannot get address", e);
        }
    }
}