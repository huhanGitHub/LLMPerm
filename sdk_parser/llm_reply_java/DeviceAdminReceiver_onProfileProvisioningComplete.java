import android.app.admin.DeviceAdminReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

@RunWith(AndroidJUnit4.class)
@Config(manifest=Config.NONE)
public class DeviceAdminReceiver_onProfileProvisioningComplete {

    @Test
    public void test_DeviceAdminReceiver_onProfileProvisioningComplete() {
        // App context
        Context appContext = InstrumentationRegistry.getTargetContext();

        // Mock Activity
        ActivityController<MainActivity> activityController = Robolectric.buildActivity(MainActivity.class);
        MainActivity activity = activityController.get();

        // Creating DeviceAdminReceiver
        DeviceAdminReceiver mDeviceAdminReceiver = new DeviceAdminReceiver();

        // Creating the intent
        Intent intent = new Intent(DeviceAdminReceiver.ACTION_PROFILE_PROVISIONING_COMPLETE);
        ComponentName componentName = new ComponentName("com.example.deviceadminsample", ".SampleDeviceAdminReceiver");
        intent.setComponent(componentName);

        // triggering the onProfileProvisioningComplete method
        mDeviceAdminReceiver.onProfileProvisioningComplete(appContext, intent);
        
        // business logic to verify the effects of calling onProfileProvisioningComplete()
        // For instance, you can check if the method has set the specified setting or has started the desired activity
        // Check your business logic here
    }
}