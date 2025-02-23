import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.telephony.MbmsDownloadSession;
import android.telephony.mbms.DownloadProgressListener;
import android.telephony.mbms.DownloadRequest;
import android.telephony.mbms.FileInfo;
import android.telephony.mbms.MbmsDownloadSessionCallback;

import java.util.List;

public class MbmsDownloadSession_download {
    @RequiresApi(api = Build.VERSION_CODES.P)
    public void test_MbmsDownloadSession_download() {
        Context appContext = getApplicationContext();

        MbmsDownloadSessionCallback sessionCallback = new MbmsDownloadSessionCallback() {
            @Override
            public void onError(int errorCode, String message) {
                // Handle errors in creating a download session here
            }

            @Override
            public void onFileServicesUpdated(List<String> services) {
                // Handle updates to the list of available file services here, if necessary
            }
        };

        MbmsDownloadSession downloadSession = MbmsDownloadSession.create(appContext, sessionCallback,
                new Handler(Looper.getMainLooper()));

        // Assuming that we've already asked the middleware for a list of available files and the
        // user has selected one or more files to download
        String serviceId = "FileService"; // ID of the file service to download from
        Uri fileUri = Uri.parse("file:///tmp/downloaded_file");

        DownloadRequest request = new DownloadRequest.Builder()
                .setServiceId(serviceId)
                .setDestination(fileUri)
                .build();

        downloadSession.download(request);
        downloadSession.setDownloadProgressListener(request, new DownloadProgressListener() {
            @Override
            public void onProgressUpdated(DownloadRequest downloadRequest, FileInfo fileInfo,
                                          int currentDownloadSize, int fullDownloadSize, int currentDecodedSize,
                                          int fullDecodedSize) {
                // Handle your download progress updates here
            }
        });
    }
}