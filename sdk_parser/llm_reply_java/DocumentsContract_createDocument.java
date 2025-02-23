public class DocumentsContract_createDocument {

    public void test_DocumentsContract_createDocument(){
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Uri treeUri; 
            String mimeType;
            String displayName;

            // Place actual values for treeUri, mimeType, and displayName
            treeUri = Uri.parse("content://com.example.provider/document/12345");
            mimeType = "text/plain";
            displayName = "new_document.txt";

            ContentResolver resolver = getContentResolver();

            try {
                Uri uri = android.provider.DocumentsContract.createDocument(resolver, treeUri, mimeType, displayName);
                // Now uri points to the new document
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    // Constants and Permissions not detailed, use appropriate context to fill
    int PERMISSION_REQUEST_CODE = 100;
}