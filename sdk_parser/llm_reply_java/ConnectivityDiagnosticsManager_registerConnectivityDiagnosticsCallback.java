public class ConnectivityDiagnosticsManager_registerConnectivityDiagnosticsCallback {

    public void test_ConnectivityDiagnosticsManager_registerConnectivityDiagnosticsCallback() {
        // Check if the app has the necessary permission
        if (checkSelfPermission(Manifest.permission.CONNECTIVITY_DIAGNOSTICS)
                == PackageManager.PERMISSION_DENIED) {
            Log.e(TAG, "Connectivity diagnostics permissions denied.");
            return;
        }

        ConnectivityDiagnosticsManager connectivityDiagnosticsManager =
                (ConnectivityDiagnosticsManager)
                        getSystemService(Context.CONNECTIVITY_DIAGNOSTICS_SERVICE);

        ConnectivityDiagnosticsManager.ConnectivityDiagnosticsCallback 
                connectivityDiagnosticsCallback =
                new ConnectivityDiagnosticsManager.ConnectivityDiagnosticsCallback() {
            @Override
            public void onNetworkDiagnosticsReportAvailable(
                    Network network,
                    ConnectivityDiagnosticsManager.ConnectivityReport connectivityReport) {
                // you can use this space to implement your logic for example,
                // Log network diagnostics information.
                Log.i(TAG, "Network: " + network.toString());
                Log.i(TAG, "ConnectivityReport: " + connectivityReport.toString());
            }
        };

        // Ensure to check not null for the ConnectivityDiagnosticsManager
        if (connectivityDiagnosticsManager != null) {
            connectivityDiagnosticsManager.registerConnectivityDiagnosticsCallback(
                    new NetworkRequest.Builder().build(), 
                    getMainExecutor(), 
                    connectivityDiagnosticsCallback);
        } else {
            Log.e(TAG, "Cannot retrieve ConnectivityDiagnosticsManager");
        }
    }
}