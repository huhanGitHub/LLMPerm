public class UserDictionary_addWord {
    private void test_UserDictionary_addWord(Context context, String word, String shortcut) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // On Jelly bean and above, direct user dictionary insertion is supported
            UserDictionary.Words.addWord(context, word, UserDictionary.Words.FREQUENCY_HIGH, shortcut, Locale.getDefault());
        } else {
            // On older versions, this feature is not supported
            Toast.makeText(context, "This feature is not supported on your device.", Toast.LENGTH_SHORT).show();
        }
    }
}