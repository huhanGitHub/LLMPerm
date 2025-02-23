import android.bluetooth.le.AdvertisingSetParameters;
import android.util.Log;

public class AdvertisingSetParameters_setOwnAddressType {
    
    public void test_AdvertisingSetParameters_setOwnAddressType() {
        try {
            AdvertisingSetParameters params = new AdvertisingSetParameters.Builder()
                    .setInterval(AdvertisingSetParameters.INTERVAL_HIGH)
                    .setTxPowerLevel(AdvertisingSetParameters.TX_POWER_HIGH)
                    .setPrimaryPhy(AdvertisingSetParameters.PHY_LE_1M)
                    .setSecondaryPhy(AdvertisingSetParameters.PHY_LE_1M)
                    .setIncludeTxPower(false)
                    .setOwnAddressType(AdvertisingSetParameters.BLE_ADDRESS_SCANNABLE_CONNECTABLE)
                    .build();

            Log.d("Test", "AdvertisingSetParameters own address type: " + params.getOwnAddressType());
        } catch (Exception e) {
            Log.e("Test", "Failed to set AdvertisingSetParameters.", e);
        }
    }
    
}