import android.app.slice.SliceManager;
import android.content.Intent;
import android.net.Uri;
import androidx.core.graphics.drawable.IconCompat;
import android.app.slice.Slice;
import android.app.slice.SliceSpec;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.content.Context;
import android.app.Activity;
import android.widget.Toast;
import androidx.annotation.Nullable;
import java.util.HashSet;
import java.util.Arrays;
import androidx.slice.builders.ListBuilder;
import androidx.slice.builders.SliceAction;
import androidx.core.content.ContextCompat;


public class SliceManager_mapIntentToUri extends Activity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    public void test_SliceManager_mapIntentToUri() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://com.example.app/"));
        SliceManager sliceManager = getSystemService(SliceManager.class);
        Uri uri = sliceManager.mapIntentToUri(intent);

        //Get the slice
        Slice slice = sliceManager.bindSlice(uri, new HashSet<>(Arrays.asList(SliceSpecs.BASIC)));

        // The actions you want to create and add to the ListBuilder
        ListBuilder listBuilder = new ListBuilder(getContext(), uri, ListBuilder.INFINITY);
        listBuilder.addRow(new ListBuilder.RowBuilder()
                .setTitle("Title")
                .setSubtitle("Subtitle")
                .setPrimaryAction(new SliceAction(
                    PendingIntent.getActivity(this, 0, intent, 0),
                    IconCompat.createWithResource(getContext(), R.drawable.ic_launcher),
                    ListBuilder.ICON_IMAGE,
                    "text"
                ))
        );

        // Set the slice content
        slice = listBuilder.build();

        //Do something with the slice, for example show it in a Toast
        Toast.makeText(this, "Slice created", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public SliceManager getSystemService(@NonNull String name) {
        if (Context.SLICE_SERVICE.equals(name))
            return (SliceManager) super.getSystemService(name);
        return null;
    }

    private Context getContext() {
        return this;
    }
}