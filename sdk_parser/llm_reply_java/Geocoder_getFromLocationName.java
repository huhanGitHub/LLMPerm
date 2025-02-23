import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Geocoder_getFromLocationName {

    public List<Address> test_Geocoder_getFromLocationName(String locationName, Context context) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocationName(locationName, 1);
        } catch (IOException e) {
            Log.e("GeocoderTest", "Error occurred while trying to get address from location name", e);
        }

        return addresses;
    }
}