import android.mtp.MtpDevice;
import android.mtp.MtpObjectInfo;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.IOException;

public class MtpDevice_importFile {
    public void test_MtpDevice_importFile(MtpDevice device, int fileId, String storagePath) {
        if (device == null) {
            throw new IllegalArgumentException("MtpDevice device cannot be null");
        }

        if (fileId <= 0) {
            throw new IllegalArgumentException("File id should be greater than 0");
        }

        if (storagePath == null) {
            throw new IllegalArgumentException("File storage path cannot be null");
        }

        // Get the file info
        MtpObjectInfo fileInfo = device.getObjectInfo(fileId);
        if (fileInfo == null) {
            throw new IllegalArgumentException("File with id " + fileId + " does not exist");
        }

        ParcelFileDescriptor pfd = null;
        try {
            pfd = ParcelFileDescriptor.open(new File(storagePath, fileInfo.getName()), ParcelFileDescriptor.MODE_READ_WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Import the file from the MTP device
        if (!device.importFile(fileId, pfd.getFileDescriptor())) {
            // Handle the error
        }

        // Don't forget to clean up!
        if (pfd != null) {
            try {
                pfd.close();
            } catch (IOException e) {
                // Clean up error
            }
        }
    }
}