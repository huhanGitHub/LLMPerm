import android.content.Context;
import android.nfc.NfcAdapter;
import android.nfc.cardemulation.NfcFCardEmulation;
import android.nfc.cardemulation.NfcFServiceInfo;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

public class NfcFCardEmulation_enableService {
    private static final String TAG = "NfcFCardEmulation_enableService";

    public void test_NfcFCardEmulation_enableService(Context context) {
        try {
            // Obtain a reference to the system NFC adapter
            NfcAdapter adapter = NfcAdapter.getDefaultAdapter(context);

            // Instantiate the NfcFCardEmulation instance
            NfcFCardEmulation nfcFCardEmulation = NfcFCardEmulation.getInstance(adapter);

            // Create a new service info with default values
            List<String> systemCodes = Arrays.asList("4000", "FE00");
            List<String> dynamicSystemCodes = Arrays.asList("4010", "FE10");
            NfcFServiceInfo serviceInfo = new NfcFServiceInfo(systemCodes, dynamicSystemCodes);

            // Register the service info
            boolean serviceRegistered = nfcFCardEmulation.registerSystemCodeForService(context.getPackageName(), serviceInfo);
            if (!serviceRegistered) {
                Log.e(TAG, "Could not register NFC-F service");
                return;
            }

            // Enable the service
            boolean serviceEnabled = nfcFCardEmulation.enableService(context, context.getPackageName());
            if (!serviceEnabled) {
                Log.e(TAG, "Could not enable NFC-F service");
                return;
            }

            Log.i(TAG, "NFC-F service registered and enabled");
        } catch (Exception e) {
            Log.e(TAG, "Error during NFC-F service setup", e);
        }
    }
}