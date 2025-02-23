import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptC;

public class RenderScript_nFontCreateFromAsset {
    public void test_RenderScript_nFontCreateFromAsset(AssetManager mgr, String path) {
        // First, we need to create a new instance of RenderScript
        RenderScript rs = RenderScript.create(this);

        // Next, we create a Typeface from an asset
        Typeface typeface = Typeface.createFromAsset(mgr, path);

        // Now, we can create a ScriptC instance, which is a class generated from a Renderscript source file
        ScriptC script = new ScriptC(rs);

        // We create an allocation for the image by creating a new Allocation 
        Allocation alloc = Allocation.createTyped(rs, script.getOutput().getType());

        // We set the font and pass our typeface to the script
        script.set_font(typeface);
        script.set_output(alloc);

        // Finally, we execute our script
        script.invoke_nFontCreateFromAsset();

        // We transfer the allocation back to the bitmap
        alloc.copyTo(bitmap);
    }
}