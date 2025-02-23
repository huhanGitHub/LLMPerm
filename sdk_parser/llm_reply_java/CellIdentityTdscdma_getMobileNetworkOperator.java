import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.CellInfo;
import android.telephony.CellInfoTdscdma;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;

public class CellIdentityTdscdma_getMobileNetworkOperator {

    public void test_CellIdentityTdscdma_getMobileNetworkOperator(Context context){
        // Check READ_PHONE_STATE permission
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Permission READ_PHONE_STATE not granted", Toast.LENGTH_SHORT).show();
            return;
        }

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            List<CellInfo> cellInfoList = telephonyManager.getAllCellInfo();
            for (CellInfo cellInfo : cellInfoList) {
                if (cellInfo instanceof CellInfoTdscdma) {
                    CellInfoTdscdma cellInfoTdscdma = (CellInfoTdscdma) cellInfo;
                    String  networkOperator = cellInfoTdscdma.getCellIdentity().getMobileNetworkOperator();
                    Toast.makeText(context, "Mobile Network Operator: " + networkOperator, Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(context, "Telephony Manager is NULL", Toast.LENGTH_SHORT).show();
        }
    }
}