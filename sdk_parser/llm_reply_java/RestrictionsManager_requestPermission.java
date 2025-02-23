import android.content.RestrictionsManager;
import android.content.Context;
import android.os.Bundle;

public class RestrictionsManager_requestPermission {

    public void test_RestrictionsManager_requestPermission() {
        //First we need to get the instance of RestrictionsManager
        RestrictionsManager restrictionsManager = (RestrictionsManager) getSystemService(Context.RESTRICTIONS_SERVICE);
        
        //Now we create a bundle which will have the requested permissions
        Bundle requestedPermission = new Bundle();
        requestedPermission.putString(RestrictionsManager.KEY_PACKAGE_NAME, getPackageName());
        requestedPermission.putStringArray(RestrictionsManager.KEY_REQUESTED_PERMISSIONS, new String[]{"android.permission.CAMERA"});
        
        //Finally we call requestPermission on RestrictionsManager instance with the bundle
        
        //Here it is important to note that REQUEST_LOCAL_APPROVAL is a local approval code. Replace it with a code that fits your purpose.
        restrictionsManager.requestPermission(requestedPermission, REQUEST_LOCAL_APPROVAL);
    }
}