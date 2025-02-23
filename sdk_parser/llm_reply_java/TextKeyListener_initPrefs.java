public class TextKeyListener_initPrefs {
    public void test_TextKeyListener_initPrefs() {
        // Get existing autocap and autocorrect settings.
        int flags = 0;
        if (android.provider.settings.System.getInt(getContentResolver(),
                android.provider.Settings.System.TEXT_AUTO_CAPS, 0) > 0) {
            flags |= TextKeyListener.Capitalize.IGNORE;
        }
        if (android.provider.settings.System.getInt(getContentResolver(),
                android.provider.Settings.System.TEXT_AUTO_REPLACE, 0) > 0) {
            flags |= TextKeyListener.AUTO_TEXT_FLAG;
        }
        TextKeyListener tkl = new TextKeyListener(Capitalize.WORDS, (flags > 0));
        editText.setKeyListener(tkl);
    }
}