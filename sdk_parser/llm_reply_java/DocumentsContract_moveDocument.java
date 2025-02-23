public class DocumentsContract_moveDocument {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void test_DocumentsContract_moveDocument(Activity activity, Uri sourceUri, Uri targetDirectoryUri) {
        ContentResolver resolver = activity.getContentResolver();

        try {
            DocumentsContract.Document result = DocumentsContract.moveDocument(resolver, sourceUri, sourceUri, targetDirectoryUri);

            if (result != null) {
                Log.d("MOVE_DOCUMENT", "Document moved successfully.");
            } else {
                Log.e("MOVE_DOCUMENT", "Document move failed.");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}