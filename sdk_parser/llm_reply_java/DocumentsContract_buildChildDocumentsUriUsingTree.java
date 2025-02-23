import android.provider.DocumentsContract;
import android.net.Uri;
import android.content.ContentResolver;
import android.util.Log;

public class DocumentsContract_buildChildDocumentsUriUsingTree {
    public void test_DocumentsContract_buildChildDocumentsUriUsingTree() {
        final String AUTHORITY = "com.android.externalstorage.documents";
        final String DOCUMENT_ID = "primary";
        String treeDocumentId = DOCUMENT_ID;
        String childDocumentId = "MyChildDocumentID";

        // build child documents uri using tree
        Uri childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(
                Uri.parse(ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY), 
                treeDocumentId
        );

        // Additional part, if you want to append childDocumentId as well
        Uri childUri = DocumentsContract.buildDocumentUriUsingTree(childrenUri, childDocumentId);

        // ToCheck whether Uri was built successfully,
        String uriString = childUri.toString();

        // The uriString should look like this :
        // content://com.android.externalstorage.documents/tree/primary/document/primary%3Aapp_doc_id
        Log.d("URI Example ", "Built Children URI : " + uriString);
    }
}