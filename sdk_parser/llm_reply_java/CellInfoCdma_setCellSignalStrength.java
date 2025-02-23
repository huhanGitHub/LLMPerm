public class CellInfoCdma_setCellSignalStrength {
    public void test_CellInfoCdma_setCellSignalStrength() {
        // Check if the permission has been granted 
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Initialize the telephony manager 
            TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            
            if (telephonyManager != null) {
                // Get all the cell info objects
                List<CellInfo> cellInfos = telephonyManager.getAllCellInfo();
                // Iterate through the cell info objects 
                for (CellInfo cellInfo : cellInfos) {
                    // Check if the cell info object is of type CellInfoCdma 
                    if (cellInfo instanceof CellInfoCdma) {
                        CellInfoCdma cellInfoCdma = (CellInfoCdma) cellInfo;
                        // Get the cell signal strength 
                        CellSignalStrengthCdma cellSignalStrengthCdma = cellInfoCdma.getCellSignalStrength();
                        // Now you can do something with cellSignalStrengthCdma
                        // But, remember, you can't set it
                    }
                }
            }
        } else {
            // Request for the permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, YOUR_REQUEST_CODE);
        }
    }
}