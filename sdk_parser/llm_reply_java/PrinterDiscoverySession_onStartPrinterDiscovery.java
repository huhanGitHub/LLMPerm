public class PrinterDiscoverySession_onStartPrinterDiscovery {

    private void test_PrinterDiscoverySession_onStartPrinterDiscovery() {
        List<PrinterId> printerIds = new ArrayList<>();
        myPrinterDiscoverySession.onStartPrinterDiscovery(printerIds);
    }

    class MyPrinterDiscoverySession extends PrinterDiscoverySession {
        private final Context context;

        public MyPrinterDiscoverySession(Context context) {
            this.context = context;
        }

        @Override
        public void onStartPrinterDiscovery(List<PrinterId> priorityList) {
            Log.d("MyPrintService", "Starting printer discovery");
        }

        @Override
        public void onStopPrinterDiscovery() {
            Log.d("MyPrintService", "Stopping printer discovery");
        }
    }
}