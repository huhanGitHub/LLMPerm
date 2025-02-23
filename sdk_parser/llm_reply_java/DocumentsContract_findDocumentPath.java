public class DocumentsContract_findDocumentPath {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void test_DocumentsContract_findDocumentPath() {
        ContentResolver contentResolver = getContentResolver();
        Uri treeUri = DocumentsContract.buildDocumentUri("com.android.providers.downloads.documents", "document");

        try {
            String documentPath = DocumentsContract.findDocumentPath(contentResolver, treeUri);
            Log.d("DocumentPathTest", "The DocumentPath: " + documentPath);
        } catch (FileNotFoundException e) {
            Log.e("DocumentPathTest", "FileNotFoundException: ", e);
        }
    }
}