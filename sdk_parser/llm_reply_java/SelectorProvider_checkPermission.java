import java.nio.channels.spi.SelectorProvider;
import java.security.Permission;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;
import java.io.IOException;

public class SelectorProvider_checkPermission {

    public void test_SelectorProvider_checkPermission(Context context) {
        //Check if the application has the permission to read from the external storage
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            System.out.println("READ_EXTERNAL_STORAGE permission is not granted");
            return;
        }

        try {
            // Get the default selector provider
            SelectorProvider provider = SelectorProvider.provider();

            // Print out the provider class
            System.out.println("Provider Class : " + provider.getClass().getName());
        } catch (IOException e) {
            // Exception handling for selector provider
        }
    }
}