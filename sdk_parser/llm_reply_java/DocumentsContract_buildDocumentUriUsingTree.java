public class DocumentsContract_buildDocumentUriUsingTree {

    import android.net.Uri;
    import android.provider.DocumentsContract;
    import android.util.Log;

    public void test_DocumentsContract_buildDocumentUriUsingTree() {
        // Let's assume we have a tree URI which points to a directory on a document provider
        Uri treeUri = Uri.parse("content://com.android.externalstorage.documents/tree/primary");

        // Let's assume we have a document URI which points to a document under the above directory
        Uri documentUri = Uri.parse("content://com.android.externalstorage.documents/document/primary%3AAndroid%2Fdata");

        try {
            // This is how you get a single tree-diven document URI
            Uri treeDocumentUri = DocumentsContract.buildDocumentUriUsingTree(treeUri, DocumentsContract.getDocumentId(documentUri));

            Log.d("TestContract", "Tree Document URI: " + treeDocumentUri.toString());

        } catch (Exception e) {
            Log.e("TestContract", "Error building document URI using tree", e);
        }
    }
}