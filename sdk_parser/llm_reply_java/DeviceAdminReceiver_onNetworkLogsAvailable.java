import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class DeviceAdminReceiver_onNetworkLogsAvailable {

    DeviceAdminReceiver deviceAdminReceiver;
    DevicePolicyManager devicePolicyManager;
    ComponentName adminComponent;
    Context context;

    public DeviceAdminReceiver_onNetworkLogsAvailable(Context context, DevicePolicyManager devicePolicyManager, ComponentName adminComponent) {
        this.context = context;
        this.devicePolicyManager = devicePolicyManager;
        this.adminComponent = adminComponent;
    }

    public void test_DeviceAdminReceiver_onNetworkLogsAvailable() { 
        
        deviceAdminReceiver = new DeviceAdminReceiver() {
            @Override
            public void onNetworkLogsAvailable(Context context, Intent intent, long batchToken, int networkLogsCount) {
    
                devicePolicyManager.retrieveNetworkLogs(adminComponent, batchToken);
    
                //additional code to handle the network logs
                //...
            }
        };
        
        Intent intent = new Intent();
        long batchToken = 1L; //sample batchToken, usually comes from the OS
        int networkLogsCount = 0; //networkLogsCount, usually comes from the OS
        
        //call method
        deviceAdminReceiver.onNetworkLogsAvailable(this.context, intent, batchToken, networkLogsCount);
    }
}