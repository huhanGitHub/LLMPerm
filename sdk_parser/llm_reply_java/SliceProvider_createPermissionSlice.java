import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.slice.Slice;
import androidx.slice.SliceProvider;

public class SliceProvider_createPermissionSlice {
    @RequiresApi(api = Build.VERSION_CODES.P)
    public void test_SliceProvider_createPermissionSlice() {
        SliceProvider sliceProvider = new SliceProvider() {
            @Override
            public boolean onCreateSliceProvider() {
                return false;
            }

            @Override
            public Slice onBindSlice(Uri sliceUri) {
                return null;
            }

            @NonNull
            @Override
            public Uri onMapIntentToUri(@Nullable Intent intent) {
                return super.onMapIntentToUri(intent);
            }
        };

        Uri testUri =  Uri.parse("content://com.example.app");
        Slice s = sliceProvider.createPermissionSlice(getContext(), testUri);

        if(s == null){
            Log.d("SLICE_PROVIDER_TEST", "Slice not created");
        }else{
            Log.d("SLICE_PROVIDER_TEST", "Slice created");
        }
    }
}