public class EventLog_readEvents {
    public void test_EventLog_readEvents() {
        Log.d(TAG, "Debug log message");
        Log.i(TAG, "Info log message");
        Log.w(TAG, "Warning log message");
        Log.e(TAG, "Error log message");

        try {
            Process process = Runtime.getRuntime().exec("logcat -d");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            StringBuilder log = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(TAG)) { // Filter by your tag
                    log.append(line);
                    // Process log data here
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Make sure to define TAG in your actual implementation, e.g.,
    // private static final String TAG = "MyApp";
}