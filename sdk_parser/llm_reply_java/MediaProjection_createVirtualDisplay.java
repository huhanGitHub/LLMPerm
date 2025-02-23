import android.media.projection.MediaProjection;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.view.Surface;
import android.content.Context;

public class MediaProjection_createVirtualDisplay {
    public void test_MediaProjection_createVirtualDisplay(Context context, MediaProjection mediaProjection) {
        String surfaceName = "DummySurface";
        int screenDensity = context.getResources().getDisplayMetrics().densityDpi;
        Surface dummySurface = null;  //Replace this with real surface
        int width = 1080;  //Replace with real width
        int height = 1920; //Replace with real height
        int flags = 0;

        // create a virtual display
        VirtualDisplay virtualDisplay = mediaProjection.createVirtualDisplay(
            surfaceName,
            width,
            height,
            screenDensity,
            flags,
            dummySurface,
            null,   //Display Callback
            null    //Handler for Display Callback
        );
    }
}