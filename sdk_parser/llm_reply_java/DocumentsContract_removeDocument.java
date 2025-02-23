public class DocumentsContract_removeDocument {
    @RequiresApi(api = Build.VERSION_CODES.O) 
    public void test_DocumentsContract_removeDocument() {
        try {
            ContentResolver resolver = getContentResolver(); // Getting the ContentResolver instance
            Uri docUri = DocumentsContract.buildDocumentUri("com.android.providers.media.documents", "image:3952"); // Replace with your document URI
            Uri treeUri = DocumentsContract.buildTreeDocumentUri("com.android.providers.media.documents", "image:"); // Replace with your tree URI

            // You should perform the operation on a non-UI thread
            new Thread(() -> {
                try {
                    // Take persistable permissions before delete
                    final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
                    getContentResolver().takePersistableUriPermission(treeUri, takeFlags);

                    // Perform delete operation with check
                    boolean result = DocumentsContract.deleteDocument(resolver, docUri);
                    // boolean result = DocumentsContract.removeDocument(resolver, docUri, treeUri); // replace 'deleteDocument' with 'removeDocument' in target SDK 30

                    if (result) {
                        Log.d("Document delete", "The document was successfully deleted.");
                    } else {
                        Log.d("Document delete", "The document delete operation failed.");
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}