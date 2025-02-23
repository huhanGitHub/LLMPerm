import android.app.Activity;
import android.content.Context;
import android.print.PrintJob;
import android.print.PrintManager;
import android.widget.Toast;

import java.util.List;

public class PrintJob_cancel extends Activity {
    public void test_PrintJob_cancel() {
        // Step 1: Get system service PRINT_SERVICE.
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);

        // Step 2: Get all print jobs.
        List<PrintJob> printJobs = printManager.getPrintJobs();

        // Step 3: Cancel all print jobs.
        for (PrintJob printJob : printJobs) {
            // Check if the job is not already cancelled or completed
            if (printJob.isQueued() || printJob.isStarted()) {
                // Cancel the print job
                printJob.cancel();
            }
        }

        // Optional: Show a toast message indicating that all print jobs are cancelled.
        Toast.makeText(this, "All print jobs are cancelled.", Toast.LENGTH_SHORT).show();
    }
}