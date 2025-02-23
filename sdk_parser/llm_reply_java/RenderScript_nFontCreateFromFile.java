import android.content.Context;
import android.graphics.Typeface;
import android.renderscript.RenderScript;
import android.util.Log;
import java.io.File;

public class RenderScript_nFontCreateFromFile {

    private RenderScript mRenderScript;

    public void test_RenderScript_nFontCreateFromFile(Context context) {
        String fontPath = "path_to_file";

        try {
            File fontFile = new File(fontPath);
            if(fontFile.exists()) {
                Typeface typeface = Typeface.createFromFile(fontPath);
                android.graphics.Typeface.Script scriptFont = android.graphics.Typeface.create(mRenderScript, typeface);
                Log.i("RenderScriptTest", "Font created: " + scriptFont);
            } else {
                Log.e("RenderScriptTest", "Font file doesn't exist: " + fontPath);
            }
        } catch (Exception e) {
            Log.e("RenderScriptTest", "Error creating font from file: " + e.getMessage());
        }
    }

    public void setRenderScript(RenderScript mRenderScript) {
        this.mRenderScript = mRenderScript;
    }
}