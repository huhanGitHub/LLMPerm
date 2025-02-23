import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.widget.Toast;

public class Allocation_createFromBitmap {

    private Bitmap mBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);

    public void test_Allocation_createFromBitmap(MainActivity mainActivity) {
        RenderScript rs = RenderScript.create(mainActivity);

        Allocation allocation = Allocation.createFromBitmap(rs, mBitmap);

        allocation.destroy();
        rs.destroy();

        Toast.makeText(mainActivity, "Allocation created from Bitmap successfully", Toast.LENGTH_LONG).show();
    }
}