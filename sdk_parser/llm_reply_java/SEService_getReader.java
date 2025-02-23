import android.se.omapi.Reader;
import android.se.omapi.SEService;
import android.util.Log;

public class SEService_getReader {
    private SEService seService;
    private Reader reader;

    public SEService_getReader(SEService seService) {
        this.seService = seService;
        test_SEService_getReader();
    }

    private void test_SEService_getReader() {
        if (seService == null) {
            Log.e("test_SEService_getReader", "SEService is null");
            return;
        }
        if (!seService.isConnected()) {
            Log.e("test_SEService_getReader", "SEService is not connected");
            return;
        }
        // Get the Reader array.
        Reader[] readers = seService.getReaders();
        if (readers.length > 0) {
            // Assume the first reader for the test.
            reader = readers[0];
            Log.d("test_SEService_getReader", "Reader name: " + reader.getName());
        } else {
            Log.e("test_SEService_getReader", "No reader available");
        }
    }
}