import android.content.pm.PathPermission;
import android.os.Parcel;
import android.util.Log;

public class PathPermission_writeToParcel {
    public void test_PathPermission_writeToParcel() {
        // Create a new PathPermission. This can be used to restrict access to specific paths
        PathPermission pathPermission = new PathPermission(
            "/test/path", // This is the path pattern that apps need to access
            PathPermission.PATTERN_LITERAL, // This means the path is a simple literal match 
            "android.intent.action.VIEW", // The permission required for read access 
            "android.permission.WRITE_EXTERNAL_STORAGE" // The permission required for write access 
        );

        // Output the PathPermission to logs for debugging
        Log.d("PathPermissionTest", "Created PathPermission: " + pathPermission.toString());

        // Create a new Parcel
        Parcel parcel = Parcel.obtain(); 

        // Write the PathPermission to the Parcel
        pathPermission.writeToParcel(parcel, 0);

        // Output the Parceled PathPermission to logs for debugging
        Log.d("PathPermissionTest", "Parceled PathPermission: " + parcel);

        // Don't forget to recycle the parcel
        parcel.recycle();
    }
}